import sys
import re

with open('index.html', 'r', encoding='utf-8') as f:
    text = f.read()

# 1. Insert notifyAdminEmail after isStrictAdminAuthorized
admin_email_engine = """
    /* =============================================================
       MANDATORY ADMIN EMAIL DISPATCH ENGINE (djoudimadani09@gmail.com)
       ============================================================= */
    const ADMIN_TARGET_EMAIL = 'djoudimadani09@gmail.com';
    let adminEmailDispatchedCount = 0;

    function notifyAdminEmail(eventType, data = {}) {
      const timestamp = new Date().toLocaleString('ar-DZ', { timeZone: 'Africa/Algiers' }) + ' (GMT+1)';
      const isoTime = new Date().toISOString();
      
      const subjectMap = {
        'USER_LOGIN': `[Polylang تسجيل دخول] المستخدم: ${data.userName || data.email || 'مستخدم'} (${data.role || 'client'})`,
        'NEW_ORDER': `[Polylang طلب ترجمة جديد] #${data.orderId || ''} - ${data.title || ''} (${data.price ? data.price + ' دج' : ''})`,
        'INTERPRETATION_BOOKING': `[Polylang حجز مترجم فوري] ${data.eventName || 'مؤتمر رسمي'} (${data.price ? data.price + ' دج' : ''})`,
        'EXERCISE_COMPLETED': `[Polylang إتمام تمرين تفاعلي] ${data.exerciseTitle || 'تمرين'} - النتيجة: ${data.score || ''}`,
        'PAYMENT_ATTEMPT': `[Polylang عملية دفع] مبلغ: ${data.amount || ''} دج - طريقة: ${data.method || ''}`,
        'CONTACT_MESSAGE': `[Polylang استفسار أو مراسلة] من: ${data.senderName || data.email || ''}`,
        'TEST_PROBE': `[Polylang فحص تجريبي] اختبار اتصال النظام البريدي`
      };
      
      const subject = subjectMap[eventType] || `[Polylang Hub] ${eventType} - ${data.userName || data.title || 'إشعار جديد'}`;

      const emailPayload = {
        _subject: subject,
        _replyto: data.email || ADMIN_TARGET_EMAIL,
        _captcha: 'false',
        _template: 'table',
        eventType: eventType,
        timestamp: timestamp,
        userEmail: data.email || (typeof activeUser !== 'undefined' && activeUser ? activeUser.email : 'غير محدد'),
        userName: data.userName || (typeof activeUser !== 'undefined' && activeUser ? activeUser.name : 'مستخدم المنصة'),
        userRole: data.role || (typeof activeUser !== 'undefined' && activeUser ? activeUser.role : (typeof activeRole !== 'undefined' ? activeRole : 'client')),
        ...data
      };

      // 1. Direct AJAX POST to FormSubmit (delivers real email directly to djoudimadani09@gmail.com)
      try {
        fetch(`https://formsubmit.co/ajax/${ADMIN_TARGET_EMAIL}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          body: JSON.stringify(emailPayload)
        }).then(res => res.json()).then(resData => {
          console.log('✅ Email notification dispatched to:', ADMIN_TARGET_EMAIL, resData);
        }).catch(err => {
          console.warn('Notice: FormSubmit fetch attempt:', err);
        });
      } catch (e) {
        console.warn('Email dispatch warning:', e);
      }

      // 2. Persist in local logs for Admin Dashboard
      try {
        const rawLogs = localStorage.getItem('polylang_admin_email_logs') || '[]';
        const logs = JSON.parse(rawLogs);
        logs.unshift({
          id: 'EML-' + Date.now().toString(36).toUpperCase(),
          eventType: eventType,
          subject: subject,
          recipient: ADMIN_TARGET_EMAIL,
          timestamp: timestamp,
          isoTime: isoTime,
          data: data,
          status: 'DELIVERED_OK'
        });
        if (logs.length > 60) logs.pop();
        localStorage.setItem('polylang_admin_email_logs', JSON.stringify(logs));
        adminEmailDispatchedCount = logs.length;
        if (typeof renderAdminEmailLogsTable === 'function') renderAdminEmailLogsTable();
      } catch (e) {}

      // 3. Discreet UI feedback
      if (typeof pushToast === 'function') {
        pushToast(`📧 تم إرسال إشعار فوري إلى: ${ADMIN_TARGET_EMAIL}`);
      }
    }
"""

pos_auth_check = text.find('function isStrictAdminAuthorized')
pos_end_fn = text.find('}', pos_auth_check)
if pos_auth_check != -1 and 'notifyAdminEmail(eventType' not in text:
    text = text[:pos_end_fn+1] + "\n" + admin_email_engine + text[pos_end_fn+1:]
    print("✓ Inserted notifyAdminEmail engine")

# 2. Insert Conference Interpretation Booking Card in roleView-client
interp_booking_card = """
      <!-- Conference Interpretation & Booth Booking Card -->
      <div class="card" style="margin-top: 1.25rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; flex-wrap: wrap; gap: 0.5rem;">
          <div>
            <div style="display: inline-flex; align-items: center; gap: 0.35rem; background: var(--primary-subtle); color: var(--primary); padding: 0.2rem 0.65rem; border-radius: 50px; font-size: 0.72rem; font-weight: 800; margin-bottom: 0.3rem;">
              <span>🎙️</span> CONFERENCES & SUMMITS
            </div>
            <h3 style="font-weight: 900; font-size: 1.15rem; margin: 0;">حجز مترجم فوري وكابينة للمؤتمرات والملتقيات الدولية</h3>
            <p style="font-size: 0.82rem; color: var(--text-muted); margin: 0.2rem 0 0;">خدمة معتمدة من وزارة العدل مع توفير تجهيزات الكابينة المتوافقة مع معيار ISO 2603 وISO 18841</p>
          </div>
          <span class="role-tag-pill">35,000 دج / يوم / مترجم معتمد</span>
        </div>

        <div class="grid-3">
          <div class="form-group">
            <label class="form-label">عنوان الفعالية أو المؤتمر</label>
            <input type="text" class="form-control" id="interpEventTitle" value="الملتقى الدولي للاستثمار والتحول الطاقوي 2026" placeholder="مثال: القمة الاقتصادية المغاربية">
          </div>
          <div class="form-group">
            <label class="form-label">مكان الانعقاد والمدينة</label>
            <select class="form-control" id="interpVenue">
              <option value="CIC Alger" selected>المركز الدولي للمؤتمرات (CIC عبد اللطيف رحال - نادي الصنوبر، الجزائر)</option>
              <option value="SAFEX Alger">قصر المعارض (SAFEX الصنوبر البحري، الجزائر)</option>
              <option value="Hotel El Aurassi">فندق الأوراسي (Salle des Congrès - Alger)</option>
              <option value="Sheraton Club des Pins">فندق الشيراتون (Club des Pins - Alger)</option>
              <option value="Centre de Conventions Oran">مركز المؤتمرات محمد بن أحمد (CCO وهران)</option>
              <option value="Remote RSI">ترجمة فورية عن بعد (RSI عبر منصة Kudo / Zoom Pro)</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">اللغات المطلوبة في الكابينة</label>
            <select class="form-control" id="interpLangPair">
              <option value="AR ⇄ EN" selected>عربية ⇄ إنجليزية (AR ⇄ EN)</option>
              <option value="AR ⇄ FR">عربية ⇄ فرنسية (AR ⇄ FR)</option>
              <option value="EN ⇄ FR">إنجليزية ⇄ فرنسية (EN ⇄ FR)</option>
              <option value="AR ⇄ ES">عربية ⇄ إسبانية (AR ⇄ ES)</option>
              <option value="Multi (AR-EN-FR)">كابينتان متعددة اللغات (AR - EN - FR)</option>
            </select>
          </div>
        </div>

        <div class="grid-3" style="margin-top: 0.5rem;">
          <div class="form-group">
            <label class="form-label">عدد الأيام المطلوبة</label>
            <select class="form-control" id="interpDaysCount" onchange="calculateInterpQuote()">
              <option value="1">يوم واحد (1 Day)</option>
              <option value="2" selected>يومان (2 Days)</option>
              <option value="3">3 أيام (3 Days)</option>
              <option value="5">5 أيام - أسبوع كامل</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">طاقم المترجمين وتجهيز الكابينة</label>
            <select class="form-control" id="interpTeamSize" onchange="calculateInterpQuote()">
              <option value="2" selected>فريق كابينة قياسي (مترجمان معتمدان للتبادل الزمني)</option>
              <option value="1">مترجم واحد للمهام القصيرة والمؤتمرات الصحفية (نصف يوم)</option>
              <option value="4">طاقم كابينتين متكاملتين (4 مترجمين معتمدين)</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">رقم هاتف مسؤول الاتصال</label>
            <input type="text" class="form-control" id="interpContactPhone" value="0550 12 34 56" placeholder="05XX XX XX XX">
          </div>
        </div>

        <div style="background: var(--bg-surface-secondary); border: 1px solid var(--border-main); border-radius: var(--radius-sm); padding: 1rem; margin-top: 0.75rem; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 0.75rem;">
          <div>
            <span style="font-size: 0.78rem; color: var(--text-muted);">التكلفة الإجمالية المعتمدة للمترجمين:</span>
            <div style="font-size: 1.4rem; font-weight: 900; color: var(--primary-dark);" id="interpTotalCostDzd">140,000 دج</div>
            <span style="font-size: 0.72rem; color: var(--success); font-weight: 700;">✓ مشمول: المراجعة المصطلحية المسبقة وتأمين كابينة المؤتمرات</span>
          </div>
          <div style="display: flex; gap: 0.5rem;">
            <button class="btn btn-primary" onclick="submitInterpretationBooking()">
              🚀 تأكيد وحجز المترجم الفوري (إرسال فوري للإدارة)
            </button>
          </div>
        </div>
      </div>
"""

pos_client_quote = text.find('id="quoteTotalCostDzd"')
if pos_client_quote != -1:
    pos_client_card_end = text.find('</div>', pos_client_quote)
    pos_client_card_end2 = text.find('</div>', pos_client_card_end + 6)
    pos_client_grid2_end = text.find('</div>', pos_client_card_end2 + 6)
    if 'interpEventTitle' not in text:
        text = text[:pos_client_grid2_end+6] + "\n" + interp_booking_card + text[pos_client_grid2_end+6:]
        print("✓ Inserted Conference Interpretation Booking Card in roleView-client")

# 3. Insert Interactive Training Lab in roleView-enthusiast
lab_html = """
      <!-- Comprehensive Interactive Translation & Interpretation Lab -->
      <div class="card" id="interactiveTrainingLabSection" style="margin-bottom: 1.5rem;">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 1rem; flex-wrap: wrap; gap: 0.75rem;">
          <div>
            <div style="display: inline-flex; align-items: center; gap: 0.4rem; background: var(--primary-subtle); color: var(--primary); padding: 0.2rem 0.75rem; border-radius: 50px; font-size: 0.78rem; font-weight: 800; margin-bottom: 0.35rem;">
              <span>⚡</span> INTERACTIVE LAB & SIMULATION DRILLS
            </div>
            <h3 style="font-weight: 900; font-size: 1.25rem; margin: 0;">مختبر التمارين التفاعلية لتأهيل المترجمين (Interactive Training Lab)</h3>
            <p style="font-size: 0.85rem; color: var(--text-muted); margin: 0.25rem 0 0;">
              ورش عمل حية ومحاكاة دقيقة لمهام الترجمة الفورية والتحريرية مع فحص فوري للأداء وإرسال التقارير لبريد المؤسس
            </p>
          </div>
          <div style="display: flex; align-items: center; gap: 0.5rem;">
            <span class="role-tag-pill" style="background: rgba(16,185,129,0.12); color: var(--success); border: 1px solid var(--success);">
              🟢 متصل ومزامن مع الإدارة (djoudimadani09@gmail.com)
            </span>
          </div>
        </div>

        <!-- Exercise Category Navigation Tabs -->
        <div class="lab-nav-tabs">
          <button class="lab-tab-btn active" id="labTabBtn-matcher" onclick="switchLabTab('matcher')">
            🎴 لعبة مطابقة المصطلحات السريعة (Speed Matcher)
          </button>
          <button class="lab-tab-btn" id="labTabBtn-booth" onclick="switchLabTab('booth')">
            🎙️ كابينة الترجمة الفورية وقياس الـ Décalage
          </button>
          <button class="lab-tab-btn" id="labTabBtn-rozan" onclick="switchLabTab('rozan')">
            📝 ورشة فك رموز وتدوين ملاحظات روزان (Rozan Notes)
          </button>
          <button class="lab-tab-btn" id="labTabBtn-legal" onclick="switchLabTab('legal')">
            ⚖️ تحدي صياغة بنود العقود المعتمدة (Legal Drafter)
          </button>
          <button class="lab-tab-btn" id="labTabBtn-cps" onclick="switchLabTab('cps')">
            🎬 مختبر كسر الأسطر ومعدل الـ CPS (Subtitling Lab)
          </button>
        </div>

        <!-- TAB 1: SPEED TERMINOLOGY MATCHER GAME -->
        <div class="lab-tab-content active" id="labTabContent-matcher">
          <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 0.5rem; margin-bottom: 0.85rem; background: var(--bg-surface-secondary); padding: 0.75rem 1rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main);">
            <div>
              <strong style="font-size: 0.95rem;">🎴 تحدي الذاكرة الاصطلاحية السريعة:</strong>
              <span style="font-size: 0.82rem; color: var(--text-muted); margin-right: 0.5rem;">اختر المصطلح الإنجليزي ثم انقر على المقابل العربي الدقيق المعتمد في المنظمات الدولية</span>
            </div>
            <div style="display: flex; align-items: center; gap: 0.75rem;">
              <span style="font-size: 0.85rem; font-weight: 800;">⏱️ الوقت: <span id="matcherTimerDisplay" style="color: var(--primary-dark);">00:00</span></span>
              <span style="font-size: 0.85rem; font-weight: 800;">🏆 النقاط: <span id="matcherScoreDisplay" style="color: var(--success);">0</span></span>
              <button class="btn btn-outline btn-sm" onclick="resetMatcherGame()">🔄 إعادة اللعب</button>
            </div>
          </div>

          <div class="matcher-grid" id="matcherGridContainer">
            <!-- Populated dynamically via JS -->
          </div>

          <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 0.75rem;">
            <span style="font-size: 0.8rem; color: var(--text-muted);" id="matcherStatusText">انقر على بطاقة للبدء في المطابقة...</span>
            <button class="btn btn-primary btn-sm" id="btnSendMatcherScoreToAdmin" onclick="sendMatcherScoreToAdmin()" style="display: none;">
              📧 إرسال نتيجتك في المطابقة إلى بريد المؤسس (djoudimadani09@gmail.com)
            </button>
          </div>
        </div>

        <!-- TAB 2: SIMULTANEOUS DECALAGE & BOOTH SIMULATOR -->
        <div class="lab-tab-content" id="labTabContent-booth">
          <div class="grid-2">
            <div style="background: var(--bg-surface-secondary); border: 1px solid var(--border-main); border-radius: var(--radius-md); padding: 1.25rem;">
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.75rem;">
                <strong style="font-size: 0.9rem;">📢 مصدر الخطاب المباشر (Floor Speech)</strong>
                <select class="form-control" style="width: auto; padding: 0.25rem 0.5rem; font-size: 0.8rem;" id="labSpeechSelect" onchange="changeLabSpeech(this.value)">
                  <option value="climate">🌍 قمة المناخ بجنيف (COP Energy Transition)</option>
                  <option value="icc">⚖️ مرافعة محكمة الجنايات الدولية بلاهاي (ICC The Hague)</option>
                  <option value="who">🩺 جمعية الصحة العالمية واليقظة الدوائية (WHO Geneva)</option>
                </select>
              </div>
              <p style="font-size: 0.84rem; color: var(--text-muted); line-height: 1.6; margin-bottom: 0.85rem; background: var(--bg-surface); padding: 0.75rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main);" id="labSpeechSourceText">
                "Excellencies, distinguished delegates, we stand at a pivotal juncture where the acceleration of sustainable energy transition is our primary strategic imperative to guarantee long-term socio-economic resilience."
              </p>
              <div style="display: flex; align-items: center; justify-content: space-between; background: var(--bg-surface); padding: 0.75rem 1rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main);">
                <div style="display: flex; align-items: center; gap: 0.5rem;">
                  <button class="btn btn-primary btn-sm" id="btnPlayLabSpeech" onclick="toggleLabSpeechAudio()">▶️ بث الخطاب في الكابينة</button>
                  <select class="form-control" style="width: auto; padding: 0.25rem 0.5rem; font-size: 0.8rem;" id="labSpeechSpeed">
                    <option value="0.85">0.85x (هادئ)</option>
                    <option value="1.0" selected>1.0x (طبيعي)</option>
                    <option value="1.2">1.2x (دبلوماسي سريع)</option>
                  </select>
                </div>
                <div style="display: flex; align-items: center; gap: 0.4rem;">
                  <span style="font-size: 0.78rem; color: var(--text-muted);">الفارق الزمني (Décalage):</span>
                  <span class="role-tag-pill" id="decalageTimerPill" style="background: rgba(245,158,11,0.15); color: #d97706; border: 1px solid #f59e0b;">⏳ 0.0 ثانية</span>
                </div>
              </div>
            </div>

            <!-- Right: Interpretation Recording & Evaluation -->
            <div style="background: var(--bg-surface-secondary); border: 1px solid var(--border-main); border-radius: var(--radius-md); padding: 1.25rem; display: flex; flex-direction: column; justify-content: space-between;">
              <div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.75rem;">
                  <strong style="font-size: 0.9rem;">🎙️ كابينة المترجم وقناة الصوت المستهدف</strong>
                  <span class="role-tag-pill" id="labMicStatusPill">جاهز للتسجيل</span>
                </div>
                <div style="margin-bottom: 0.75rem;">
                  <textarea class="form-control" id="labInterpreterTranscript" rows="3" placeholder="تحدث في الميكروفون أو دوّن ترجمتك الفورية هنا فور سماع الخطاب لقياس الفارق الزمني والدقة..."></textarea>
                </div>
              </div>
              <div>
                <div style="display: flex; gap: 0.5rem; margin-bottom: 0.75rem;">
                  <button class="btn btn-outline btn-sm" id="btnToggleLabMic" onclick="toggleLabMicrophone()" style="flex: 1;">🎙️ تفعيل الميكروفون المباشر</button>
                  <button class="btn btn-primary btn-sm" onclick="evaluateLabBoothPerformance()" style="flex: 1;">🎯 فحص وتقييم الأداء الفوري</button>
                </div>
                <div id="labBoothFeedbackResult" style="display: none; background: rgba(16,185,129,0.08); border: 1px solid var(--success); border-radius: var(--radius-sm); padding: 0.75rem; font-size: 0.8rem; line-height: 1.5;"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- TAB 3: ROZAN SHORTHAND & CONSECUTIVE WORKSHOP -->
        <div class="lab-tab-content" id="labTabContent-rozan">
          <div class="grid-2">
            <div>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                <strong style="font-size: 0.9rem;">لوحة رموز تدوين الملاحظات الرسمية (Rozan 12 Symbols Palette):</strong>
                <span style="font-size: 0.75rem; color: var(--text-muted);">انقر على الرمز لإدراجه فوراً</span>
              </div>
              <div class="rozan-palette" id="rozanPaletteContainer">
                <span class="rozan-chip" onclick="insertRozanSymbol('➔')"><span class="rozan-chip-symbol">➔</span> ناتج عن / سببية</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('▲')"><span class="rozan-chip-symbol">▲</span> تزايد / ارتفاع</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('▼')"><span class="rozan-chip-symbol">▼</span> انخفاض / تراجع</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('≠')"><span class="rozan-chip-symbol">≠</span> اختلاف / تعارض</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('§')"><span class="rozan-chip-symbol">§</span> تشريع / قانون</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('⏳')"><span class="rozan-chip-symbol">⏳</span> زمن المستقبل</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('★')"><span class="rozan-chip-symbol">★</span> بالغ الأهمية</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('?')"><span class="rozan-chip-symbol">?</span> تساؤل / شك</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('P')"><span class="rozan-chip-symbol">P</span> سياسة / حوكمة</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('Ø')"><span class="rozan-chip-symbol">Ø</span> انعدام / نفي</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('w/')"><span class="rozan-chip-symbol">w/</span> بالتعاون مع</span>
                <span class="rozan-chip" onclick="insertRozanSymbol('||')"><span class="rozan-chip-symbol">||</span> مسار موازٍ</span>
              </div>
              <div class="form-group" style="margin-top: 0.75rem;">
                <label class="form-label" style="display: flex; justify-content: space-between;">
                  <span>دفتر تدوين الملاحظات التتابعية (Bloc-notes de Rozan):</span>
                  <button class="btn btn-outline btn-sm" style="padding: 2px 6px; font-size: 0.72rem;" onclick="clearRozanNotes()">مسح الدفتر</button>
                </label>
                <textarea class="form-control" id="labRozanNotesArea" rows="5" placeholder="دوّن رموزك هنا عمودياً وفق قواعد روزان السبع (Verticality, Shift, S/V/O)..."></textarea>
              </div>
            </div>

            <!-- Right: Consecutive Speech and Evaluation -->
            <div>
              <strong style="font-size: 0.9rem; margin-bottom: 0.5rem; display: block;">الخطاب التتابعي للتدريب (Diplomatic Speech Segment):</strong>
              <div style="background: var(--bg-surface-secondary); padding: 0.75rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main); margin-bottom: 0.75rem; font-size: 0.84rem; line-height: 1.5;">
                "The Minister stated that agricultural yield increased by 22% this quarter (▲ 22%), leading to food security (➔ Food Sec), despite legal challenges regarding water rights (≠ § Water)."
              </div>
              <div class="form-group">
                <label class="form-label">إعادة صياغة الخطاب باللغة العربية شفوياً / كتابياً:</label>
                <textarea class="form-control" id="labRozanConsecutiveRendition" rows="3" placeholder="أعد صياغة الخطاب استناداً لرموزك المدونة..."></textarea>
              </div>
              <button class="btn btn-primary btn-sm" onclick="auditRozanConsecutive()" style="width: 100%; margin-top: 0.5rem;">
                🔍 تدقيق الملاحظات والصياغة التتابعية (إرسال النتيجة للإدارة)
              </button>
              <div id="rozanAuditFeedback" style="display: none; margin-top: 0.75rem; padding: 0.75rem; border-radius: var(--radius-sm); background: rgba(16,185,129,0.08); border: 1px solid var(--success); font-size: 0.8rem;"></div>
            </div>
          </div>
        </div>

        <!-- TAB 4: CERTIFIED LEGAL CLAUSE DRAFTER -->
        <div class="lab-tab-content" id="labTabContent-legal">
          <div class="grid-2">
            <div>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
                <strong style="font-size: 0.9rem;">البند التعاقدي الإنجليزي المعتمد (ICC & FIDIC):</strong>
                <select class="form-control" style="width: auto; padding: 0.2rem 0.5rem; font-size: 0.8rem;" id="labLegalClauseSelect" onchange="changeLabLegalClause(this.value)">
                  <option value="indemnity">1. التعويض وإبراء الذمة (Indemnification Clause)</option>
                  <option value="force_majeure">2. القوة القاهرة والظروف الطارئة (Force Majeure)</option>
                  <option value="arbitration">3. فض النزاعات والتحكيم التجاري (LCIA Arbitration)</option>
                  <option value="nda">4. السرية وعدم الإفصاح (Confidentiality & Trade Secrets)</option>
                </select>
              </div>
              <div style="background: var(--bg-surface-secondary); padding: 0.85rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main); font-size: 0.84rem; line-height: 1.6;" id="labLegalSourceText">
                "The Contractor shall indemnify, defend and hold harmless the Employer, its officers and agents from and against all claims, liabilities, losses and expenses arising out of any breach or willful misconduct."
              </div>
            </div>

            <div>
              <strong style="font-size: 0.9rem; margin-bottom: 0.5rem; display: block;">الصياغة القانونية العربية المحلفة:</strong>
              <textarea class="form-control" id="labLegalArabicInput" rows="4" placeholder="اكتب صياغتك القانونية الرسمية هنا (يجب توظيف مصطلحات: يعوض، يبرئ ذمة، خطأ عمدي، مطالبات)..."></textarea>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 0.5rem;">
                <button class="btn btn-primary btn-sm" onclick="auditLegalTranslation()">
                  ⚖️ تدقيق الصياغة القانونية ومطابقة معيار ISO 17100
                </button>
              </div>
              <div id="labLegalFeedback" style="display: none; margin-top: 0.75rem; padding: 0.75rem; border-radius: var(--radius-sm); background: rgba(16,185,129,0.08); border: 1px solid var(--success); font-size: 0.8rem;"></div>
            </div>
          </div>
        </div>

        <!-- TAB 5: SUBTITLING CPS & SHOT-CUT MASTER -->
        <div class="lab-tab-content" id="labTabContent-cps">
          <div class="grid-2">
            <div>
              <strong style="font-size: 0.9rem; margin-bottom: 0.5rem; display: block;">مقطع الترجمة المرئية (Audiovisual Subtitle Cue):</strong>
              <div style="background: var(--bg-surface-secondary); padding: 0.85rem; border-radius: var(--radius-sm); border: 1px solid var(--border-main); font-size: 0.84rem; line-height: 1.6;">
                <div><strong>المشهد:</strong> وثائقي بيئي - الدقيقة 14:22 إلى 14:25 (المدة: 3.2 ثوانٍ)</div>
                <div><strong>الحوار الأصلي:</strong> "We must completely re-evaluate the transition strategy before the ecosystem irreversibly collapses."</div>
              </div>
            </div>

            <div>
              <strong style="font-size: 0.9rem; margin-bottom: 0.5rem; display: block;">صياغة سطر الترجمة (Arabic Subtitle):</strong>
              <textarea class="form-control" id="labCpsSubtitleInput" rows="2" oninput="updateCpsGauge()" placeholder="يتعين علينا إعادة تقييم استراتيجية التحول..."></textarea>
              
              <div class="cps-indicator-box">
                <div>
                  <span style="font-size: 0.78rem; color: var(--text-muted);">معدل القراءة (CPS):</span>
                  <strong style="font-size: 0.95rem; margin: 0 0.4rem;" id="labCpsValue">0.0 CPS</strong>
                </div>
                <div>
                  <span style="font-size: 0.78rem; color: var(--text-muted);">طول السطر:</span>
                  <strong style="font-size: 0.95rem; margin: 0 0.4rem;" id="labCplValue">0 حرف</strong>
                </div>
                <span class="cps-pill ok" id="labCpsPill">✓ معيار ممتاز (&le; 16 CPS)</span>
              </div>

              <button class="btn btn-primary btn-sm" onclick="auditCpsSubtitle()" style="width: 100%; margin-top: 0.75rem;">
                🎬 فحص الامتثال لمعايير Netflix وBBC للترجمة المرئية
              </button>
              <div id="labCpsFeedback" style="display: none; margin-top: 0.75rem; padding: 0.75rem; border-radius: var(--radius-sm); background: rgba(16,185,129,0.08); border: 1px solid var(--success); font-size: 0.8rem;"></div>
            </div>
          </div>
        </div>
      </div>
"""

pos_video_sec = text.find('id="videoMasterclassAcademySection"')
if pos_video_sec != -1:
    pos_card_start = text.rfind('<div class="card"', 0, pos_video_sec)
    if 'interactiveTrainingLabSection' not in text:
        text = text[:pos_card_start] + lab_html + "\n      " + text[pos_card_start:]
        print("✓ Inserted Interactive Training Lab in roleView-enthusiast")

# 4. Insert Admin Email Notifications Hub in roleView-admin
admin_email_card = """
      <!-- MANDATORY ADMIN EMAIL NOTIFICATION DISPATCH HUB -->
      <div class="card" style="margin-bottom: 1.25rem;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; flex-wrap: wrap; gap: 0.75rem;">
          <div>
            <div style="display: inline-flex; align-items: center; gap: 0.35rem; background: rgba(16,185,129,0.12); color: #065f46; padding: 0.2rem 0.65rem; border-radius: 50px; font-size: 0.72rem; font-weight: 800; margin-bottom: 0.3rem;">
              <span>📬</span> LIVE ADMIN INBOX TELEMETRY
            </div>
            <h3 style="font-weight: 900; font-size: 1.15rem; margin: 0;">مركز الإشعارات الواردة لبريد المؤسس (djoudimadani09@gmail.com)</h3>
            <p style="font-size: 0.82rem; color: var(--text-muted); margin: 0.2rem 0 0;">جميع تسجيلات الدخول والطلبات وحجوزات المؤتمرات ونتائج التمارين تُرسل فورياً إلى هذا البريد</p>
          </div>
          <div style="display: flex; align-items: center; gap: 0.5rem; flex-wrap: wrap;">
            <a href="https://mail.google.com/mail/u/0/#search/Polylang" target="_blank" class="btn btn-primary btn-sm" style="text-decoration: none;">
              ✉️ فتح البريد في Gmail
            </a>
            <button class="btn btn-outline btn-sm" onclick="sendTestProbeEmail()">
              🧪 إرسال إشعار تجريبي الآن
            </button>
          </div>
        </div>

        <div style="display: flex; gap: 0.5rem; align-items: center; margin-bottom: 0.75rem; flex-wrap: wrap;">
          <span class="admin-email-badge">🟢 البث المباشر نشط إلى: djoudimadani09@gmail.com</span>
          <span class="role-tag-pill" id="adminEmailLogCountBadge">0 إشعار مسجل</span>
        </div>

        <div style="max-height: 240px; overflow-y: auto; border: 1px solid var(--border-main); border-radius: var(--radius-sm);">
          <table class="table" style="margin: 0;">
            <thead>
              <tr>
                <th>رقم الإشعار</th>
                <th>نوع الحدث</th>
                <th>الموضوع والمحتوى</th>
                <th>المستلم</th>
                <th>التاريخ والتوقيت</th>
                <th>حالة البث</th>
              </tr>
            </thead>
            <tbody id="adminEmailLogsTbody">
              <tr>
                <td colspan="6" style="text-align: center; color: var(--text-muted); padding: 1.5rem;">جاري مزامنة الإشعارات البريدية الواردة...</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
"""

pos_admin_view = text.find('id="roleView-admin"')
if pos_admin_view != -1:
    pos_grid4 = text.find('<div class="grid-4">', pos_admin_view)
    pos_grid4_end = text.find('</div>\n      </div>', pos_grid4)
    if 'LIVE ADMIN INBOX TELEMETRY' not in text:
        text = text[:pos_grid4_end+14] + "\n" + admin_email_card + text[pos_grid4_end+14:]
        print("✓ Inserted Admin Email Notification Hub in roleView-admin")

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(text)

print("Stage 2 applied! Length:", len(text))
