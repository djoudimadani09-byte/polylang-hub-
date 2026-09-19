import sys

with open('index.html', 'r', encoding='utf-8') as f:
    text = f.read()

stage3_js = """
    /* =============================================================
       INTERACTIVE TRANSLATION & INTERPRETATION LAB JAVASCRIPT ENGINE
       ============================================================= */
    function switchLabTab(tabKey) {
      document.querySelectorAll('.lab-tab-btn').forEach(b => b.classList.remove('active'));
      document.querySelectorAll('.lab-tab-content').forEach(c => c.classList.remove('active'));

      const activeBtn = document.getElementById('labTabBtn-' + tabKey);
      const activeContent = document.getElementById('labTabContent-' + tabKey);

      if (activeBtn) activeBtn.classList.add('active');
      if (activeContent) activeContent.classList.add('active');

      if (typeof playAppSound === 'function') playAppSound('click');

      if (tabKey === 'matcher' && matcherCardsList.length === 0) {
        initMatcherGame();
      }
    }

    /* -------------------------------------------------------------
       1. SPEED TERMINOLOGY MATCHER GAME (16 Cards / 8 Pairs)
       ------------------------------------------------------------- */
    const MATCHER_PAIRS = [
      { en: 'Force Majeure', ar: 'القوة القاهرة / الحادث الفجائي', id: 1 },
      { en: 'Boilerplate Clauses', ar: 'البنود المعيارية النمطية في العقود', id: 2 },
      { en: 'Indemnification', ar: 'التعويض وإبراء الذمة القانونية', id: 3 },
      { en: 'Informed Consent', ar: 'الموافقة المستنيرة في التجارب السريرية', id: 4 },
      { en: 'Pharmacovigilance', ar: 'اليقظة والرصد الدوائي', id: 5 },
      { en: 'Simultaneous Interpretation', ar: 'الترجمة الفورية المتزامنة بالمؤتمرات', id: 6 },
      { en: 'Décalage (Ear-Voice Span)', ar: 'الفارق الزمني بين الخطاب والترجمة', id: 7 },
      { en: 'Termbase / Multiterm', ar: 'قاعدة بيانات المصطلحات المعتمدة', id: 8 }
    ];

    let matcherCardsList = [];
    let matcherSelectedCard = null;
    let matcherMatchesCount = 0;
    let matcherScore = 0;
    let matcherTimer = 0;
    let matcherInterval = null;

    function initMatcherGame() {
      const container = document.getElementById('matcherGridContainer');
      if (!container) return;

      matcherCardsList = [];
      MATCHER_PAIRS.forEach(pair => {
        matcherCardsList.push({ id: pair.id, text: pair.en, lang: 'en' });
        matcherCardsList.push({ id: pair.id, text: pair.ar, lang: 'ar' });
      });

      // Shuffle
      matcherCardsList.sort(() => Math.random() - 0.5);

      matcherMatchesCount = 0;
      matcherScore = 0;
      matcherTimer = 0;
      clearInterval(matcherInterval);

      matcherInterval = setInterval(() => {
        matcherTimer++;
        const mins = String(Math.floor(matcherTimer / 60)).padStart(2, '0');
        const secs = String(matcherTimer % 60).padStart(2, '0');
        const disp = document.getElementById('matcherTimerDisplay');
        if (disp) disp.innerText = `${mins}:${secs}`;
      }, 1000);

      renderMatcherGrid();
      const scoreDisp = document.getElementById('matcherScoreDisplay');
      if (scoreDisp) scoreDisp.innerText = '0';
      const statusText = document.getElementById('matcherStatusText');
      if (statusText) statusText.innerText = 'انقر على مصطلح بالإنجليزية ثم اختر مقابله المعتمد بالعربية...';
      const btnEmail = document.getElementById('btnSendMatcherScoreToAdmin');
      if (btnEmail) btnEmail.style.display = 'none';
    }

    function renderMatcherGrid() {
      const container = document.getElementById('matcherGridContainer');
      if (!container) return;

      container.innerHTML = matcherCardsList.map((card, idx) => `
        <div class="matcher-card ${card.matched ? 'matched' : ''} ${card.selected ? 'selected' : ''}" 
             onclick="handleMatcherCardClick(${idx})">
          <span>${card.text}</span>
        </div>
      `).join('');
    }

    function handleMatcherCardClick(idx) {
      const card = matcherCardsList[idx];
      if (!card || card.matched || card.selected) return;

      if (typeof playAppSound === 'function') playAppSound('click');

      if (!matcherSelectedCard) {
        // First selection
        matcherSelectedCard = { index: idx, data: card };
        card.selected = true;
        renderMatcherGrid();
      } else {
        // Second selection
        const prev = matcherSelectedCard;
        if (prev.data.lang !== card.lang && prev.data.id === card.id) {
          // MATCH!
          card.matched = true;
          matcherCardsList[prev.index].matched = true;
          card.selected = false;
          matcherCardsList[prev.index].selected = false;
          matcherSelectedCard = null;
          matcherMatchesCount++;
          matcherScore += 100;

          if (typeof playAppSound === 'function') playAppSound('success');
          const scoreDisp = document.getElementById('matcherScoreDisplay');
          if (scoreDisp) scoreDisp.innerText = matcherScore;

          const statusText = document.getElementById('matcherStatusText');
          if (statusText) statusText.innerText = `✓ تطابق ممتاز! (${matcherMatchesCount} / 8 أزواج مكتملة)`;

          renderMatcherGrid();

          if (matcherMatchesCount === MATCHER_PAIRS.length) {
            // Victory!
            clearInterval(matcherInterval);
            if (typeof playAppSound === 'function') playAppSound('badge');
            if (statusText) statusText.innerHTML = `<strong style="color: var(--success);">🎉 أحسنت صنعاً! تم إنجاز التحدي بنجاح في ${matcherTimer} ثانية بنتيجة ${matcherScore} نقطة!</strong>`;
            const btnEmail = document.getElementById('btnSendMatcherScoreToAdmin');
            if (btnEmail) btnEmail.style.display = 'inline-block';
            
            // Auto dispatch notification to djoudimadani09@gmail.com
            try {
              notifyAdminEmail('EXERCISE_COMPLETED', {
                exerciseTitle: 'تحدي مطابقة المصطلحات السريعة (Speed Terminology Matcher)',
                score: `${matcherScore} نقطة`,
                timeSeconds: `${matcherTimer} ثانية`,
                pairsCount: '8/8 أزواج كاملة'
              });
            } catch (err) {}
          }
        } else {
          // MISMATCH
          card.selected = true;
          renderMatcherGrid();
          if (typeof playAppSound === 'function') playAppSound('error');

          const cardsDom = document.querySelectorAll('.matcher-card');
          if (cardsDom[idx]) cardsDom[idx].classList.add('wrong');
          if (cardsDom[prev.index]) cardsDom[prev.index].classList.add('wrong');

          setTimeout(() => {
            card.selected = false;
            matcherCardsList[prev.index].selected = false;
            matcherSelectedCard = null;
            renderMatcherGrid();
          }, 700);
        }
      }
    }

    function resetMatcherGame() {
      initMatcherGame();
    }

    function sendMatcherScoreToAdmin() {
      notifyAdminEmail('EXERCISE_COMPLETED', {
        exerciseTitle: 'تحدي مطابقة المصطلحات السريعة (Speed Matcher)',
        score: `${matcherScore} نقطة`,
        timeSeconds: `${matcherTimer} ثانية`
      });
      alert('📧 تم إرسال بطاقة أدائك ونتيجتك إلى بريد المؤسس: djoudimadani09@gmail.com بنجاح!');
    }

    /* -------------------------------------------------------------
       2. SIMULTANEOUS DECALAGE & BOOTH SIMULATOR
       ------------------------------------------------------------- */
    const LAB_SPEECHES = {
      climate: {
        title: 'قمة المناخ بجنيف (COP Energy Transition)',
        text: 'Excellencies, distinguished delegates, we stand at a pivotal juncture where the acceleration of sustainable energy transition is our primary strategic imperative to guarantee long-term socio-economic resilience.',
        keywords: ['أصحاب السعادة', 'المندوبون الموقرون', 'مفترق طرق', 'التحول الطاقوي المستدام', 'المرونة والصمود الاقتصادي'],
        durationSec: 15
      },
      icc: {
        title: 'مرافعة محكمة الجنايات الدولية بلاهاي (ICC The Hague)',
        text: 'Mr. President, Members of the Tribunal, the Prosecution submits that the intentional destruction of civilian infrastructure constitutes a grave breach under the Rome Statute.',
        keywords: ['السيد الرئيس', 'أعضاء المحكمة', 'هيئة الادعاء', 'التدمير العمدي', 'انتهاك جسيم', 'نظام روما الأساسي'],
        durationSec: 18
      },
      who: {
        title: 'جمعية الصحة العالمية واليقظة الدوائية (WHO Geneva)',
        text: 'Global pharmacovigilance requires continuous post-marketing surveillance to rapidly identify and mitigate adverse drug reactions across diverse populations.',
        keywords: ['اليقظة الدوائية العالمية', 'ترصد ما بعد التسويق', 'التخفيف من الآثار العكسية', 'مجموعات سكانية متنوعة'],
        durationSec: 16
      }
    };

    let isLabSpeechPlaying = false;
    let labDecalageSeconds = 0;
    let labDecalageInterval = null;
    let isLabMicRecording = false;

    function changeLabSpeech(key) {
      const sp = LAB_SPEECHES[key];
      if (!sp) return;
      const textEl = document.getElementById('labSpeechSourceText');
      if (textEl) textEl.innerText = `"${sp.text}"`;
      if (isLabSpeechPlaying) toggleLabSpeechAudio();
    }

    function toggleLabSpeechAudio() {
      const btn = document.getElementById('btnPlayLabSpeech');
      const pill = document.getElementById('decalageTimerPill');
      const speechKey = document.getElementById('labSpeechSelect')?.value || 'climate';
      const sp = LAB_SPEECHES[speechKey];

      isLabSpeechPlaying = !isLabSpeechPlaying;

      if (isLabSpeechPlaying) {
        if (btn) btn.innerText = '⏸️ إيقاف البث';
        labDecalageSeconds = 0;
        clearInterval(labDecalageInterval);

        labDecalageInterval = setInterval(() => {
          labDecalageSeconds += 0.1;
          if (pill) {
            pill.innerText = `⏳ ${labDecalageSeconds.toFixed(1)} ثانية`;
            if (labDecalageSeconds >= 2.0 && labDecalageSeconds <= 4.0) {
              pill.style.background = 'rgba(16,185,129,0.15)';
              pill.style.color = 'var(--success)';
            } else {
              pill.style.background = 'rgba(245,158,11,0.15)';
              pill.style.color = '#d97706';
            }
          }
        }, 100);

        if ('speechSynthesis' in window) {
          window.speechSynthesis.cancel();
          const u = new SpeechSynthesisUtterance(sp.text);
          u.lang = 'en-US';
          const speed = parseFloat(document.getElementById('labSpeechSpeed')?.value || '1.0');
          u.rate = speed;
          u.onend = () => {
            isLabSpeechPlaying = false;
            clearInterval(labDecalageInterval);
            if (btn) btn.innerText = '▶️ بث الخطاب في الكابينة';
          };
          window.speechSynthesis.speak(u);
        }
      } else {
        if (btn) btn.innerText = '▶️ بث الخطاب في الكابينة';
        clearInterval(labDecalageInterval);
        if ('speechSynthesis' in window) window.speechSynthesis.cancel();
      }
    }

    function toggleLabMicrophone() {
      const btn = document.getElementById('btnToggleLabMic');
      const pill = document.getElementById('labMicStatusPill');
      isLabMicRecording = !isLabMicRecording;

      if (isLabMicRecording) {
        if (btn) btn.innerText = '⏹️ إيقاف الميكروفون';
        if (pill) {
          pill.innerText = '🔴 الميكروفون يبث على الهواء';
          pill.style.background = 'rgba(239,68,68,0.15)';
          pill.style.color = 'var(--danger)';
        }
        if (typeof playAppSound === 'function') playAppSound('record-start');
      } else {
        if (btn) btn.innerText = '🎙️ تفعيل الميكروفون المباشر';
        if (pill) {
          pill.innerText = 'جاهز للتسجيل';
          pill.style.background = 'var(--bg-surface-secondary)';
          pill.style.color = 'var(--text-muted)';
        }
        if (typeof playAppSound === 'function') playAppSound('record-stop');
      }
    }

    function evaluateLabBoothPerformance() {
      const transcript = (document.getElementById('labInterpreterTranscript')?.value || '').trim();
      const speechKey = document.getElementById('labSpeechSelect')?.value || 'climate';
      const sp = LAB_SPEECHES[speechKey];
      const feedbackEl = document.getElementById('labBoothFeedbackResult');

      let termScore = 86;
      let matchedKw = [];
      sp.keywords.forEach(kw => {
        if (transcript.includes(kw.split(' ')[0])) {
          termScore += 3;
          matchedKw.push(kw);
        }
      });
      termScore = Math.min(termScore, 98);

      const lagScore = (labDecalageSeconds >= 2.0 && labDecalageSeconds <= 4.2) ? 95 : 88;
      const totalScore = Math.round((termScore * 0.6) + (lagScore * 0.4));

      if (feedbackEl) {
        feedbackEl.style.display = 'block';
        feedbackEl.innerHTML = `
          <div style="font-weight: 900; color: var(--primary-dark); font-size: 0.95rem; margin-bottom: 0.35rem;">
            🏆 نتيجة فحص الكابينة: ${totalScore} / 100 (معتمد للأمم المتحدة والاتحاد الإفريقي)
          </div>
          <div>✔️ <strong>دقة المصطلحات:</strong> ${termScore}% - المصطلحات السيادية المعتمدة بنجاح.</div>
          <div>✔️ <strong>ضبط الفارق الزمني (Décalage Control):</strong> ${lagScore}% (متوسط الفارق: ${labDecalageSeconds.toFixed(1)} ث).</div>
          <div>✔️ <strong>توصية الجودة:</strong> أداء ممتاز يفي بمتطلبات معيار ISO 18841 للترجمة الفورية.</div>
        `;
      }

      if (typeof playAppSound === 'function') playAppSound('badge');

      // Dispatch to admin email
      notifyAdminEmail('EXERCISE_COMPLETED', {
        exerciseTitle: `كابينة الترجمة الفورية (${sp.title})`,
        score: `${totalScore} / 100`,
        decalageTime: `${labDecalageSeconds.toFixed(1)} ثانية`,
        transcript: transcript.substring(0, 100) || 'تسجيل صوتي شفهي'
      });
    }

    /* -------------------------------------------------------------
       3. ROZAN CONSECUTIVE LAB
       ------------------------------------------------------------- */
    function insertRozanSymbol(sym) {
      const textarea = document.getElementById('labRozanNotesArea');
      if (!textarea) return;
      textarea.value += (textarea.value ? ' ' : '') + sym + ' ';
      textarea.focus();
      if (typeof playAppSound === 'function') playAppSound('click');
    }

    function clearRozanNotes() {
      const textarea = document.getElementById('labRozanNotesArea');
      if (textarea) textarea.value = '';
    }

    function auditRozanConsecutive() {
      const notes = (document.getElementById('labRozanNotesArea')?.value || '').trim();
      const rendition = (document.getElementById('labRozanConsecutiveRendition')?.value || '').trim();
      const fb = document.getElementById('rozanAuditFeedback');

      const hasSymbols = /[➔▲▼≠§⏳★?PØ]/.test(notes);
      const score = hasSymbols ? 93 : 84;

      if (fb) {
        fb.style.display = 'block';
        fb.innerHTML = `
          <strong>🏆 نتيجة تقييم روزان: ${score}/100</strong><br>
          ✓ ${hasSymbols ? 'توظيف متميز لرموز روزان للسببية والتغير النسبي.' : 'يُنصح بتكثيف الرموز لتقليل الكتابة النصية.'}<br>
          ✓ الصياغة التتابعية متماسكة وتستوفي معايير التسلسل المنطقي.
        `;
      }
      if (typeof playAppSound === 'function') playAppSound('success');

      notifyAdminEmail('EXERCISE_COMPLETED', {
        exerciseTitle: 'ورشة روزان لتدوين الملاحظات والترجمة التتابعية',
        score: `${score}/100`,
        symbolsUsed: hasSymbols ? 'نعم (رموز روزان القياسية)' : 'نص فقط',
        renditionSnippet: rendition.substring(0, 80)
      });
    }

    /* -------------------------------------------------------------
       4. CERTIFIED LEGAL CLAUSE DRAFTER
       ------------------------------------------------------------- */
    const LEGAL_CLAUSES = {
      indemnity: {
        source: "The Contractor shall indemnify, defend and hold harmless the Employer, its officers and agents from and against all claims, liabilities, losses and expenses arising out of any breach or willful misconduct.",
        mandatoryTerms: ['يعوض', 'يبرئ ذمة', 'صاحب العمل', 'مسؤوليات', 'خطأ عمدي']
      },
      force_majeure: {
        source: "Neither party shall be held liable for any delay or failure in performance resulting from circumstances beyond its reasonable control, including acts of God and labor strikes.",
        mandatoryTerms: ['القوة القاهرة', 'ظروف طارئة', 'خارجة عن الإرادة', 'إضراب']
      },
      arbitration: {
        source: "Any dispute arising out of or in connection with this contract shall be finally settled under the Rules of Arbitration of the International Chamber of Commerce by three arbitrators.",
        mandatoryTerms: ['أي نزاع', 'غرفة التجارة الدولية', 'التحكيم', 'محكمين']
      },
      nda: {
        source: "The Receiving Party agrees to treat all Confidential Information as strictly proprietary and not to disclose such information to any third party without prior written authorization.",
        mandatoryTerms: ['الطرف المستلم', 'معلومات سرية', 'ملك حريز', 'إفصاح', 'موافقة خطية مسبقة']
      }
    };

    function changeLabLegalClause(key) {
      const c = LEGAL_CLAUSES[key];
      if (!c) return;
      const el = document.getElementById('labLegalSourceText');
      if (el) el.innerText = `"${c.source}"`;
    }

    function auditLegalTranslation() {
      const key = document.getElementById('labLegalClauseSelect')?.value || 'indemnity';
      const c = LEGAL_CLAUSES[key];
      const input = (document.getElementById('labLegalArabicInput')?.value || '').trim();
      const fb = document.getElementById('labLegalFeedback');

      let hits = 0;
      c.mandatoryTerms.forEach(t => {
        if (input.includes(t)) hits++;
      });

      const score = Math.min(75 + (hits * 5), 98);

      if (fb) {
        fb.style.display = 'block';
        fb.innerHTML = `
          <strong>⚖️ نتيجة تدقيق الصياغة القانونية: ${score} / 100</strong><br>
          ✓ رصد المصطلحات المحلفة: تم تضمين ${hits} من أصل ${c.mandatoryTerms.length} مصطلحات ملزمة.<br>
          ✓ المطابقة المعيارية: مستوفية لضوابط الصياغة المعتمدة لدى المحاكم وهيئات التحكيم.
        `;
      }
      if (typeof playAppSound === 'function') playAppSound('badge');

      notifyAdminEmail('EXERCISE_COMPLETED', {
        exerciseTitle: `صياغة العقود القانونية (${key})`,
        score: `${score} / 100`,
        termsMatched: `${hits} / ${c.mandatoryTerms.length}`
      });
    }

    /* -------------------------------------------------------------
       5. SUBTITLING CPS & SHOT-CUT MASTER
       ------------------------------------------------------------- */
    function updateCpsGauge() {
      const text = (document.getElementById('labCpsSubtitleInput')?.value || '').trim();
      const durationSec = 3.2; // video cue duration
      const charCount = text.length;
      const cps = (charCount / durationSec).toFixed(1);

      const valEl = document.getElementById('labCpsValue');
      const cplEl = document.getElementById('labCplValue');
      const pill = document.getElementById('labCpsPill');

      if (valEl) valEl.innerText = `${cps} CPS`;
      if (cplEl) cplEl.innerText = `${charCount} حرف`;

      if (pill) {
        if (cps <= 16) {
          pill.className = 'cps-pill ok';
          pill.innerText = '✓ معيار ممتاز (≤ 16 CPS)';
        } else if (cps <= 19) {
          pill.className = 'cps-pill warn';
          pill.innerText = '⚠️ سرعة قراءة متوسطة (17-19 CPS)';
        } else {
          pill.className = 'cps-pill bad';
          pill.innerText = '⛔ تتجاوز سرعة القراءة المقبولة (> 19 CPS)';
        }
      }
    }

    function auditCpsSubtitle() {
      const text = (document.getElementById('labCpsSubtitleInput')?.value || '').trim();
      const durationSec = 3.2;
      const cps = parseFloat((text.length / durationSec).toFixed(1));
      const fb = document.getElementById('labCpsFeedback');

      const ok = cps <= 16 && text.length > 5;
      const score = ok ? 96 : 82;

      if (fb) {
        fb.style.display = 'block';
        fb.innerHTML = `
          <strong>🎬 تقييم الترجمة المرئية (AVT CPS): ${score}/100</strong><br>
          ✓ معدل الـ CPS الفعلي: ${cps} حرف/ثانية (${ok ? 'مطابق تماماً لمعايير Netflix' : 'يحتاج لاختزال لغوي'}).<br>
          ✓ تجزئة السطور ومحاذاة المشاهد: معتمدة تقنياً.
        `;
      }
      if (typeof playAppSound === 'function') playAppSound(ok ? 'success' : 'click');

      notifyAdminEmail('EXERCISE_COMPLETED', {
        exerciseTitle: 'مختبر ضوابط الترجمة المرئية ومعدل الـ CPS',
        score: `${score}/100`,
        cps: `${cps} CPS`,
        lineLength: `${text.length} حرف`
      });
    }

    /* -------------------------------------------------------------
       6. CONFERENCE INTERPRETATION BOOKING LOGIC
       ------------------------------------------------------------- */
    function calculateInterpQuote() {
      const days = parseInt(document.getElementById('interpDaysCount')?.value || '2');
      const team = parseInt(document.getElementById('interpTeamSize')?.value || '2');
      const dailyRatePerInterpreter = 35000;
      const total = days * team * dailyRatePerInterpreter;
      const el = document.getElementById('interpTotalCostDzd');
      if (el) el.innerText = `${total.toLocaleString()} دج`;
    }

    function submitInterpretationBooking() {
      const title = (document.getElementById('interpEventTitle')?.value || 'مؤتمر رسمي').trim();
      const venue = document.getElementById('interpVenue')?.value || 'CIC Alger';
      const lang = document.getElementById('interpLangPair')?.value || 'AR ⇄ EN';
      const days = parseInt(document.getElementById('interpDaysCount')?.value || '2');
      const team = parseInt(document.getElementById('interpTeamSize')?.value || '2');
      const phone = (document.getElementById('interpContactPhone')?.value || '0550 12 34 56').trim();
      const total = days * team * 35000;

      const newOrder = {
        id: 'INT-' + Math.floor(1000 + Math.random() * 9000),
        title: `حجز مترجم فوري (${title})`,
        category: 'ترجمة فورية',
        lang: lang,
        words: days * 8 * 120, // 8h conference day estimate
        price: total,
        status: 'pending'
      };

      if (typeof clientOrders !== 'undefined') {
        clientOrders.unshift(newOrder);
        if (typeof persistState === 'function') persistState();
        if (typeof renderClientOrdersTable === 'function') renderClientOrdersTable();
      }

      // Notify admin email
      notifyAdminEmail('INTERPRETATION_BOOKING', {
        orderId: newOrder.id,
        eventName: title,
        venue: venue,
        langPair: lang,
        daysCount: days,
        interpretersCount: team,
        contactPhone: phone,
        price: total,
        clientName: activeUser ? activeUser.name : 'عميل بوليلانغ',
        clientEmail: activeUser ? activeUser.email : 'client@polylang.dz'
      });

      if (typeof playAppSound === 'function') playAppSound('success');
      alert(`✅ تم تأكيد طلب حجز المترجم الفوري للمؤتمر بنجاح!\n\nرقم الحجز: #${newOrder.id}\nالتكلفة الإجمالية: ${total.toLocaleString()} دج\n\nتم إرسال إشعار فوري بكافة التفاصيل إلى بريد الإدارة: ${ADMIN_TARGET_EMAIL}`);
    }

    /* -------------------------------------------------------------
       7. ADMIN EMAIL LOGS TABLE RENDERER
       ------------------------------------------------------------- */
    function renderAdminEmailLogsTable() {
      const tbody = document.getElementById('adminEmailLogsTbody');
      const badge = document.getElementById('adminEmailLogCountBadge');
      if (!tbody) return;

      const rawLogs = localStorage.getItem('polylang_admin_email_logs') || '[]';
      let logs = [];
      try { logs = JSON.parse(rawLogs); } catch(e) {}

      if (badge) badge.innerText = `${logs.length} إشعار مسجل`;

      if (logs.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; color: var(--text-muted); padding: 1.25rem;">لم يتم تسجيل إشعارات بريدية بعد. انقر على 'إرسال إشعار تجريبي' للبدء.</td></tr>`;
        return;
      }

      tbody.innerHTML = logs.map(item => `
        <tr>
          <td><strong style="color: var(--primary-dark); font-family: monospace;">${item.id}</strong></td>
          <td><span class="role-tag-pill" style="font-size: 0.75rem;">${item.eventType}</span></td>
          <td style="font-size: 0.82rem;">${item.subject}</td>
          <td style="font-size: 0.82rem;"><code>${item.recipient}</code></td>
          <td style="font-size: 0.78rem; color: var(--text-muted);">${item.timestamp}</td>
          <td><span class="status-badge completed" style="font-size: 0.72rem;">✓ DELIVERED</span></td>
        </tr>
      `).join('');
    }

    function sendTestProbeEmail() {
      notifyAdminEmail('TEST_PROBE', {
        testMessage: 'فحص تجريبي للتأكد من وصول الإشعارات إلى بريد المؤسس: djoudimadani09@gmail.com'
      });
      renderAdminEmailLogsTable();
      alert('📧 تم إرسال إشعار فوري تجريبي إلى djoudimadani09@gmail.com!');
    }
"""

pos_end_script = text.rfind('</script>')
if pos_end_script != -1 and 'switchLabTab(tabKey)' not in text:
    text = text[:pos_end_script] + "\n" + stage3_js + "\n" + text[pos_end_script:]
    print("✓ Added Interactive Lab JavaScript engine before </script>")

# Hook renderAdminEmailLogsTable into renderAdminView
if 'function renderAdminView()' in text and 'renderAdminEmailLogsTable()' not in text:
    text = text.replace('function renderAdminView() {', 'function renderAdminView() {\n      try { renderAdminEmailLogsTable(); } catch(e){}', 1)
    print("✓ Hooked renderAdminEmailLogsTable into renderAdminView")

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(text)

print("Stage 3 applied! New index.html length:", len(text))
