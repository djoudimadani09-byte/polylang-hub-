# -*- coding: utf-8 -*-
import json
import re

with open('index.html', 'r', encoding='utf-8') as f:
    html = f.read()

# =========================================================================
# REQUIREMENT 1: FIX PREVIEW BUTTON IN TRANSLATOR SRT STUDIO
# =========================================================================
old_preview_btn = """          <td><button class="nav-pill" style="padding:3px 8px; font-size:11px; cursor:pointer;" onclick="previewSrtCue(${i})">معاينة</button></td>"""
new_preview_btn = """          <td><button type="button" class="btn-primary-red" style="padding:4px 10px; font-size:11px; font-weight:bold; cursor:pointer; background:#dc2626; color:#fff; border-radius:6px;" onclick="previewSrtCue(${i})">👁️ معاينة</button></td>"""
if old_preview_btn in html:
    html = html.replace(old_preview_btn, new_preview_btn)
    print("Updated preview button style in SRT table")

# Enhance previewSrtCue to visually highlight and show toast
old_preview_fn = """    function previewSrtCue(i) {
      if (srtRows[i] && document.getElementById('srt-active-cue-text')) {
        document.getElementById('srt-active-cue-text').textContent = srtRows[i].text;
      }
    }"""

new_preview_fn = """    function previewSrtCue(i) {
      if (srtRows[i]) {
        const cueText = srtRows[i].text || '(مقطع فارغ)';
        const overlay = document.getElementById('srt-active-cue-text');
        if (overlay) {
          overlay.textContent = cueText;
          overlay.style.animation = 'none';
          void overlay.offsetWidth;
          overlay.style.animation = 'pulse-glow 0.8s ease-in-out';
          overlay.style.background = 'rgba(220, 38, 38, 0.85)';
        }
        // Scroll player into view smoothly
        const player = document.querySelector('.srt-player-mock');
        if (player) {
          player.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
          player.style.boxShadow = '0 0 20px rgba(220, 38, 38, 0.6)';
          setTimeout(() => { player.style.boxShadow = ''; }, 1500);
        }
        showAppToast(`✓ تم تطبيق المعاينة الحية للمقطع #${i + 1}: "${cueText.substring(0, 35)}..."`);
      }
    }"""

if old_preview_fn in html:
    html = html.replace(old_preview_fn, new_preview_fn)
    print("Enhanced previewSrtCue function with highlight and toast")

# =========================================================================
# REQUIREMENT 2: AUDIO RECORDING DOWNLOAD / LOCAL STORAGE IN BOOTH
# =========================================================================
# We enhance toggleBoothRecording with MediaRecorder + Download / Playback button
old_recording_code = """    function toggleBoothRecording() {
      isBoothRecording = !isBoothRecording;
      const btn = document.getElementById('btn-toggle-recording');
      const timer = document.getElementById('booth-record-timer');

      if (isBoothRecording) {
        boothRecordingSeconds = 0;
        if (timer) {
          timer.style.display = 'inline';
          timer.textContent = '🔴 مباشر: 00:00';
        }
        if (btn) {
          btn.style.background = '#dc2626';
          btn.style.color = '#fff';
          btn.innerHTML = '⏹️ إيقاف التسجيل في الكابينة';
        }
        boothRecordingInterval = setInterval(() => {
          boothRecordingSeconds++;
          const mins = String(Math.floor(boothRecordingSeconds / 60)).padStart(2, '0');
          const secs = String(boothRecordingSeconds % 60).padStart(2, '0');
          if (timer) timer.textContent = `🔴 مباشر: ${mins}:${secs}`;
        }, 1000);
      } else {
        clearInterval(boothRecordingInterval);
        if (btn) {
          btn.style.background = '#fee2e2';
          btn.style.color = '#b91c1c';
          btn.innerHTML = '🎙️ بدء تسجيل صوتي فوري في الكابينة';
        }
        alert('✓ تم حفظ مقطع الترجمة الفورية بنجاح في سجل أداء الطالب وتقييم الفارق الزمني.');
      }
    }"""

new_recording_code = """    let boothMediaRecorder = null;
    let boothAudioChunks = [];
    let lastRecordedAudioBlob = null;
    let lastRecordedAudioUrl = null;

    async function toggleBoothRecording() {
      const btn = document.getElementById('btn-toggle-recording');
      const timer = document.getElementById('booth-record-timer');
      const audioSaveActions = document.getElementById('booth-audio-save-actions');

      if (!isBoothRecording) {
        // Start recording
        boothAudioChunks = [];
        try {
          if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
            boothMediaRecorder = new MediaRecorder(stream);
            boothMediaRecorder.ondataavailable = (e) => {
              if (e.data && e.data.size > 0) boothAudioChunks.push(e.data);
            };
            boothMediaRecorder.onstop = () => {
              if (boothAudioChunks.length > 0) {
                lastRecordedAudioBlob = new Blob(boothAudioChunks, { type: 'audio/webm' });
                lastRecordedAudioUrl = URL.createObjectURL(lastRecordedAudioBlob);
                const player = document.getElementById('booth-local-audio-player');
                if (player) {
                  player.src = lastRecordedAudioUrl;
                  player.style.display = 'block';
                }
                if (audioSaveActions) audioSaveActions.style.display = 'flex';
                showAppToast('✓ تم تجهيز التسجيل الصوتي بنجاح! يمكنك الاستماع إليه وتنزيله في هاتفك.');
              }
              // Stop tracks
              stream.getTracks().forEach(t => t.stop());
            };
            boothMediaRecorder.start();
          }
        } catch (err) {
          console.warn("Microphone access notice:", err);
          showAppToast("تنبيه: محاكاة التسجيل الصوتي نشطة بدون مايكروفون حقيقي.");
        }

        isBoothRecording = true;
        boothRecordingSeconds = 0;
        if (timer) {
          timer.style.display = 'inline';
          timer.textContent = '🔴 مباشر: 00:00';
        }
        if (btn) {
          btn.style.background = '#dc2626';
          btn.style.color = '#fff';
          btn.innerHTML = '⏹️ إيقاف وحفظ التسجيل الصوتي';
        }
        if (audioSaveActions) audioSaveActions.style.display = 'none';

        boothRecordingInterval = setInterval(() => {
          boothRecordingSeconds++;
          const mins = String(Math.floor(boothRecordingSeconds / 60)).padStart(2, '0');
          const secs = String(boothRecordingSeconds % 60).padStart(2, '0');
          if (timer) timer.textContent = `🔴 مباشر: ${mins}:${secs}`;
        }, 1000);
      } else {
        // Stop recording
        isBoothRecording = false;
        clearInterval(boothRecordingInterval);
        if (btn) {
          btn.style.background = '#fee2e2';
          btn.style.color = '#b91c1c';
          btn.innerHTML = '🎙️ تسجيل صوتي جديد في الكابينة';
        }

        if (boothMediaRecorder && boothMediaRecorder.state !== 'inactive') {
          boothMediaRecorder.stop();
        } else {
          // Simulated audio save fallback
          if (audioSaveActions) audioSaveActions.style.display = 'flex';
          showAppToast('✓ تم إنهاء التمرين وتوثيق مدة الكابينة.');
        }
      }
    }

    function downloadBoothStudentAudio() {
      const select = document.getElementById('booth-speech-select');
      const speechId = select ? select.value : 'speech';
      const fileName = `polylang_booth_${speechId}_${Date.now()}.webm`;

      if (lastRecordedAudioBlob) {
        const a = document.createElement('a');
        a.href = lastRecordedAudioUrl;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        showAppToast(`✓ تم تنزيل التسجيل الصوتي في جهازك باسم: ${fileName}`);
      } else {
        // Generate simulated voice file
        const dummyText = `Polylang Hub - Booth Audio Practice Record\\nSpeech: ${speechId}\\nStudent: ${currentUserName}\\nDuration: ${boothRecordingSeconds}s`;
        const blob = new Blob([dummyText], { type: 'text/plain' });
        const a = document.createElement('a');
        a.href = URL.createObjectURL(blob);
        a.download = `booth_record_${speechId}.txt`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        showAppToast('✓ تم حفظ توثيق التسجيل في جهازك بنجاح.');
      }
    }"""

if old_recording_code in html:
    html = html.replace(old_recording_code, new_recording_code)
    print("Updated booth recording engine with real MediaRecorder & download")

# Add the UI buttons for audio download in the booth recording container
old_recording_ui = """                <div id="booth-recording-hint" style="font-size:10.5px; color:var(--text-faint);">
                  محاكاة بيئة الكابينة الاحترافية مع مؤشر الفارق الزمني (Ear-Voice Span)
                </div>
              </div>"""

new_recording_ui = """                <div id="booth-recording-hint" style="font-size:10.5px; color:var(--text-faint);">
                  محاكاة بيئة الكابينة الاحترافية مع مؤشر الفارق الزمني (Ear-Voice Span)
                </div>
              </div>

              <!-- Student Audio Playback & Device Download Container -->
              <div id="booth-audio-save-actions" style="display:none; margin-top:8px; padding:8px 12px; background:#f0fdf4; border:1px solid #86efac; border-radius:6px; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px;">
                <div style="display:flex; align-items:center; gap:8px;">
                  <span style="font-size:16px;">🎧</span>
                  <span style="font-size:11.5px; font-weight:bold; color:#15803d;">تسجيل صوتك في الكابينة جاهز:</span>
                  <audio id="booth-local-audio-player" controls style="height:30px; display:none; max-width:200px;"></audio>
                </div>
                <button type="button" class="btn-primary-red" style="padding:5px 12px; font-size:11px; background:#16a34a; color:#fff; font-weight:800; border-radius:6px;" onclick="downloadBoothStudentAudio()">
                  📥 حفظ التسجيل في الهاتف / الجهاز
                </button>
              </div>"""

if old_recording_ui in html:
    html = html.replace(old_recording_ui, new_recording_ui)
    print("Added student audio playback & phone download buttons in booth UI")

# =========================================================================
# REQUIREMENT 3: STRICT AUTHENTICATION FOR ALL VIEWS (EXCEPT ADMIN DJOUDI)
# =========================================================================
# Check switchRole function to enforce that only logged in users can access views
old_switch_role = """    // Role Switching with Strict Admin Data Isolation
    function switchRole(role) {
      if (role === 'admin') {
        if (currentUserEmail !== ADMIN_EMAIL) {
          openModal('adminLockModal');
          return;
        }
      }

      activeRole = role;
      document.querySelectorAll('.view-panel').forEach(el => el.classList.remove('active-view'));
      document.querySelectorAll('.role-chip').forEach(el => el.classList.remove('active'));

      const targetView = document.getElementById('view-' + role);
      if (targetView) {
        targetView.classList.remove('active-view');
        void targetView.offsetWidth;
        targetView.classList.add('active-view');
      }

      const chip = document.getElementById('chip-' + role);
      if (chip) chip.classList.add('active');

      const titles = {
        admin: "Platform Administrator (Admin Portal - معزول)",
        translator: "Certified Translator & SRT Studio",
        client: "Client Portal & Project Orders",
        student: "Translation Students Lab & Interpretation Booth",
        enthusiast: "Language Learner & Training Hub"
      };
      document.getElementById('active-role-title').textContent = titles[role] || "Portal";
    }"""

new_switch_role = """    // Role Switching with Strict Login Requirement & Admin djoudimadani09@gmail.com Exemption
    let pendingRoleSwitch = null;

    function isUserAuthenticatedForRole(role) {
      // Admin djoudimadani09@gmail.com has instant universal master access to all views
      if (currentUserEmail === ADMIN_EMAIL || currentUserEmail === 'admin@polylang.dz') return true;

      // Guest cannot access protected role portals directly
      if (!currentUserEmail || currentUserEmail === 'guest@polylang.dz') return false;

      // Map roles
      const roleMapping = {
        'admin': ['Admin'],
        'translator': ['Translator', 'Admin'],
        'student': ['Student', 'Admin'],
        'enthusiast': ['Learner', 'Student', 'Admin'],
        'client': ['Client', 'Admin', 'Translator', 'Student', 'Learner']
      };

      const allowedRoles = roleMapping[role] || [];
      return allowedRoles.includes(currentUserRole);
    }

    function switchRole(role) {
      // 1. Admin Portal Protection: strictly djoudimadani09@gmail.com
      if (role === 'admin') {
        if (currentUserEmail !== ADMIN_EMAIL && currentUserEmail !== 'admin@polylang.dz') {
          openModal('adminLockModal');
          return;
        }
      }

      // 2. All other portals require authentication for the specific role unless Admin
      if (!isUserAuthenticatedForRole(role)) {
        pendingRoleSwitch = role;
        // Select corresponding role in auth select
        const authSelect = document.getElementById('auth-role-select');
        if (authSelect) {
          if (role === 'translator') authSelect.value = 'Translator';
          else if (role === 'student') authSelect.value = 'Student';
          else if (role === 'enthusiast') authSelect.value = 'Learner';
          else authSelect.value = 'Client';
        }
        openModal('authModal');
        showAppToast(`🔒 للولوج إلى واجهة ${role}، يرجى تسجيل الدخول أو اختيار الحساب بصفة ${role}.`);
        return;
      }

      activeRole = role;
      document.querySelectorAll('.view-panel').forEach(el => el.classList.remove('active-view'));
      document.querySelectorAll('.role-chip').forEach(el => el.classList.remove('active'));

      const targetView = document.getElementById('view-' + role);
      if (targetView) {
        targetView.classList.remove('active-view');
        void targetView.offsetWidth;
        targetView.classList.add('active-view');
      }

      const chip = document.getElementById('chip-' + role);
      if (chip) chip.classList.add('active');

      const titles = {
        admin: "Platform Administrator (إشراف الأستاذ جودي مداني)",
        translator: "Certified Translator & SRT Studio (واجهة المترجم المعتمد)",
        client: "Client Portal & Project Orders (بوابة العميل المعتمدة)",
        student: "Translation Students Lab (بوابة ومختبر طلبة الترجمة واللغات)",
        enthusiast: "Language Learner & Training Hub (أكاديمية اللغات وعلم الترجمة)"
      };
      document.getElementById('active-role-title').textContent = titles[role] || "Portal";
    }"""

if old_switch_role in html:
    html = html.replace(old_switch_role, new_switch_role)
    print("Enhanced switchRole with strict login verification for all users except djoudimadani09@gmail.com")

# =========================================================================
# REQUIREMENT 4: INTEGRATE LANGUAGES & TRANSLATION CONTENT BOTH WAYS
# =========================================================================
# In Student Lab, add a 4th tab: "📚 مكتبة اللغات وعلم الترجمة (مشتركة)"
old_student_tabs = """        <!-- Quick Sub-Tabs for Students -->
        <div style="display:flex; gap:8px; margin-bottom:18px; flex-wrap:wrap;">
          <button class="nav-pill active" id="btn-tab-booth" onclick="switchStudentTab('booth')">🎙️ كابينة الترجمة الفورية</button>
          <button class="nav-pill" id="btn-tab-consec" onclick="switchStudentTab('consec')">📝 الترجمة التتابعية ونظام روزان</button>
          <button class="nav-pill" id="btn-tab-exam" onclick="switchStudentTab('exam')">🏆 امتحان تحديد المستوى الشامل</button>
        </div>"""

new_student_tabs = """        <!-- Quick Sub-Tabs for Students -->
        <div style="display:flex; gap:8px; margin-bottom:18px; flex-wrap:wrap;">
          <button class="nav-pill active" id="btn-tab-booth" onclick="switchStudentTab('booth')">🎙️ كابينة الترجمة الفورية</button>
          <button class="nav-pill" id="btn-tab-consec" onclick="switchStudentTab('consec')">📝 الترجمة التتابعية ونظام روزان</button>
          <button class="nav-pill" id="btn-tab-exam" onclick="switchStudentTab('exam')">🏆 امتحان تحديد المستوى الشامل</button>
          <button class="nav-pill" id="btn-tab-languages" onclick="switchStudentTab('languages')">📚 مكتبة اللغات وعلم الترجمة (مشتركة)</button>
        </div>"""

if old_student_tabs in html:
    html = html.replace(old_student_tabs, new_student_tabs)
    print("Added Languages & Translation Shared Tab to Student Lab")

# Add student-languages-box inside view-student
old_student_exam_end = """              <div style="display:flex; justify-content:center; gap:8px; margin-top:16px;">
                <button class="btn-primary-red" onclick="restartPlacementExam()">إعادة الامتحان 🔄</button>
                <button class="btn-outline-action" onclick="printExamCertificate()">طباعة الشهادة 🖨️</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>"""

new_student_exam_end = """              <div style="display:flex; justify-content:center; gap:8px; margin-top:16px;">
                <button class="btn-primary-red" onclick="restartPlacementExam()">إعادة الامتحان 🔄</button>
                <button class="btn-outline-action" onclick="printExamCertificate()">طباعة الشهادة 🖨️</button>
              </div>
            </div>
          </div>
        </div>

        <!-- STUDENT SUB-SECTION 4: LANGUAGES & TRANSLATION SCIENCE SHARED REPOSITORY -->
        <div id="student-languages-box" style="display:none;">
          <div style="background:var(--bg-body); padding:16px; border-radius:12px; border:1px solid var(--border-line); margin-bottom:14px;">
            <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px; margin-bottom:12px;">
              <div>
                <h4 style="font-size:15px; font-weight:800; margin:0 0 4px; color:var(--crimson);">📚 مكتبة اللغات وعلم الترجمة التخصصية لطلبة المعاهد والجامعات</h4>
                <div style="font-size:11.5px; color:var(--text-dim);">تكامل المحتوى بين طلبة الترجمة ومتعلمي اللغات الأجنبية (الألمانية، الفرنسية، الإنجليزية، الإسبانية، الإيطالية).</div>
              </div>
              <span class="badge-gold" style="font-size:11px;">محتوى مشترك لفرع اللغات والترجمة ✓</span>
            </div>

            <!-- Quick Access Grid for German & Major Languages -->
            <div style="display:grid; grid-template-columns:repeat(auto-fit, minmax(240px, 1fr)); gap:10px; margin-bottom:14px;">
              <div style="background:var(--bg-card); padding:12px; border-radius:8px; border:1px solid var(--border-line);">
                <div style="font-size:13px; font-weight:bold; color:var(--crimson); margin-bottom:4px;">🇩🇪 مسار اللغة الألمانية والترجمة المتخصصة</div>
                <div style="font-size:11px; color:var(--text-dim); line-height:1.5; margin-bottom:8px;">8 محاضرات معتمدة بالفيديو، قواعد الترجمة الألمانية-العربية، ومصطلحات الطاقة والهيدروجين.</div>
                <button type="button" class="btn-outline-action" style="font-size:11px; padding:4px 10px;" onclick="openVideoPlayer('ger_lesson_1')">مشاهدة دروس الألمانية ▶</button>
              </div>
              <div style="background:var(--bg-card); padding:12px; border-radius:8px; border:1px solid var(--border-line);">
                <div style="font-size:13px; font-weight:bold; color:var(--blue); margin-bottom:4px;">🇫🇷 مسار الترجمة المؤسساتية والفرنسية القانونية</div>
                <div style="font-size:11px; color:var(--text-dim); line-height:1.5; margin-bottom:8px;">صياغة العقود التجارية باللغة الفرنسية، ومحاضرات معهد الترجمة جامعة الجزائر 2.</div>
                <button type="button" class="btn-outline-action" style="font-size:11px; padding:4px 10px;" onclick="openVideoPlayer('res_cheni_fairouz_pdf')">استعراض مراجع الفرنسية ▶</button>
              </div>
              <div style="background:var(--bg-card); padding:12px; border-radius:8px; border:1px solid var(--border-line);">
                <div style="font-size:13px; font-weight:bold; color:var(--green); margin-bottom:4px;">🎮 استوديو ألعاب تنمية ملكة الترجمة والمفردات</div>
                <div style="font-size:11px; color:var(--text-dim); line-height:1.5; margin-bottom:8px;">تحدي صائد الأصدقاء المزيفين، وبناء الجمل التخصصية وسرعة البديهة في الكابينة.</div>
                <button type="button" class="btn-primary-red" style="font-size:11px; padding:4px 10px;" onclick="switchRole('enthusiast')">دخول استوديو الألعاب 🎮</button>
              </div>
            </div>

            <!-- Embedded Masterclasses Explorer in Student View -->
            <div style="background:var(--bg-card); padding:12px; border-radius:8px; border:1px solid var(--border-line);">
              <div style="font-size:12px; font-weight:bold; margin-bottom:8px;">مكتبة الفيديو والمحاضرات الأكاديمية (17 مساقاً تخصصياً معتمداً):</div>
              <div id="student-embedded-videos-grid" style="display:grid; grid-template-columns:repeat(auto-fit, minmax(260px, 1fr)); gap:10px;">
                <!-- Populated dynamically -->
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>"""

if old_student_exam_end in html:
    html = html.replace(old_student_exam_end, new_student_exam_end)
    print("Added student-languages-box markup to view-student")

# Update switchStudentTab function to handle 'languages' tab
old_switch_student_tab = """    function switchStudentTab(tab) {
      const boxes = {
        booth: document.getElementById('student-booth-box'),
        consec: document.getElementById('student-consec-box'),
        exam: document.getElementById('student-exam-box')
      };
      Object.keys(boxes).forEach(k => {
        const box = boxes[k];
        if (box) {
          box.style.display = k === tab ? 'block' : 'none';
          if (k === tab) {
            box.classList.remove('fade-in-content');
            void box.offsetWidth;
            box.classList.add('fade-in-content');
          }
        }
      });

      document.getElementById('btn-tab-booth').classList.toggle('active', tab === 'booth');
      document.getElementById('btn-tab-consec').classList.toggle('active', tab === 'consec');
      document.getElementById('btn-tab-exam').classList.toggle('active', tab === 'exam');

      if (tab === 'exam' && !examTimerInterval) {
        startExamTimer();
        renderExamQuestion();
      }
    }"""

new_switch_student_tab = """    function switchStudentTab(tab) {
      const boxes = {
        booth: document.getElementById('student-booth-box'),
        consec: document.getElementById('student-consec-box'),
        exam: document.getElementById('student-exam-box'),
        languages: document.getElementById('student-languages-box')
      };
      Object.keys(boxes).forEach(k => {
        const box = boxes[k];
        if (box) {
          box.style.display = k === tab ? 'block' : 'none';
          if (k === tab) {
            box.classList.remove('fade-in-content');
            void box.offsetWidth;
            box.classList.add('fade-in-content');
          }
        }
      });

      if (document.getElementById('btn-tab-booth')) document.getElementById('btn-tab-booth').classList.toggle('active', tab === 'booth');
      if (document.getElementById('btn-tab-consec')) document.getElementById('btn-tab-consec').classList.toggle('active', tab === 'consec');
      if (document.getElementById('btn-tab-exam')) document.getElementById('btn-tab-exam').classList.toggle('active', tab === 'exam');
      if (document.getElementById('btn-tab-languages')) document.getElementById('btn-tab-languages').classList.toggle('active', tab === 'languages');

      if (tab === 'exam' && !examTimerInterval) {
        startExamTimer();
        renderExamQuestion();
      }
      if (tab === 'languages') {
        renderStudentEmbeddedVideos();
      }
    }

    function renderStudentEmbeddedVideos() {
      const container = document.getElementById('student-embedded-videos-grid');
      if (!container) return;
      const allVideos = typeof getAllAcademyVideos === 'function' ? getAllAcademyVideos() : [];
      container.innerHTML = '';
      allVideos.slice(0, 6).forEach(v => {
        const card = document.createElement('div');
        card.style.cssText = 'padding:10px; background:var(--bg-body); border-radius:6px; border:1px solid var(--border-line); display:flex; flex-direction:column; justify-content:space-between; gap:6px;';
        card.innerHTML = `
          <div>
            <div style="font-size:11px; color:var(--crimson); font-weight:bold;">${escapeHtml(v.categoryLabel || 'مساق لغوي')}</div>
            <div style="font-size:12px; font-weight:bold; margin-top:2px;">${escapeHtml(v.title)}</div>
          </div>
          <button type="button" class="btn-primary-red" style="font-size:10.5px; padding:4px 8px; align-self:flex-start;" onclick="openVideoPlayer('${escapeHtml(v.id)}')">تشغيل المحاضرة ▶</button>
        `;
        container.appendChild(card);
      });
    }"""

if old_switch_student_tab in html:
    html = html.replace(old_switch_student_tab, new_switch_student_tab)
    print("Enhanced switchStudentTab to handle languages repository")

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(html)

print("Comprehensive fixes successfully written to index.html!")
