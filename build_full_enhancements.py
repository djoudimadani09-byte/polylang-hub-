#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import json
import re

def main():
    with open("index.html", "r", encoding="utf-8") as f:
        html = f.read()

    # 1. Ensure booth_100_speeches_data.js is loaded in head
    if "booth_100_speeches_data.js" not in html:
        html = html.replace(
            '<script src="placement_exams_100_data.js"></script>',
            '<script src="booth_100_speeches_data.js"></script>\n  <script src="placement_exams_100_data.js"></script>'
        )

    # 2. Upgrade openVideoPlayer to guarantee German videos play smoothly
    old_video_switch = """      // Default to robust web embed player for real language & translation tutorials
      switchVideoEngine('embed');"""
    new_video_switch = """      // Default to direct HTML5 stream for Facebook videos to bypass 3rd-party blocking, else embed
      if (item.url && item.url.includes('facebook.com')) {
        switchVideoEngine('mp4');
      } else {
        switchVideoEngine('embed');
      }"""
    if old_video_switch in html:
        html = html.replace(old_video_switch, new_video_switch)

    # 3. Replace the Booth UI section (student-booth-box and student-consec-box)
    old_booth_section_pattern = re.compile(
        r'<!-- STUDENT SUB-SECTION 1: SIMULTANEOUS INTERPRETATION BOOTH -->.*?<!-- STUDENT SUB-SECTION 3: COMPREHENSIVE LEVEL-PLACEMENT EXAM -->',
        re.DOTALL
    )

    new_booth_section = """<!-- STUDENT SUB-SECTION 1: SIMULTANEOUS INTERPRETATION BOOTH (100 SPEECHES) -->
        <div id="student-booth-box" style="display:block;">
          <div style="background:var(--bg-body); padding:16px; border-radius:12px; border:1px solid var(--border-line); margin-bottom:14px;">
            <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px; margin-bottom:12px;">
              <div>
                <h4 id="txt-booth-title" style="font-size:15px; font-weight:800; margin:0 0 4px;">🎙️ محاكي كابينة المؤتمرات الدولية (100 خطاب تدريبي معتمد ISO 17100)</h4>
                <div id="txt-booth-subtitle" style="font-size:11.5px; color:var(--text-dim);">خطابات واقعية طويلة تحاكي القمم الدبلوماسية، قمم الطاقة، الاقتصاد، والمنظمات الدولية.</div>
              </div>
              <div style="display:flex; align-items:center; gap:8px;">
                <span id="booth-counter-badge" class="reg-pill" style="font-size:11px; font-weight:700;">110 خطابات معتمدة</span>
                <div style="font-size:12px; font-weight:700; color:var(--crimson);">
                  <span id="txt-decalage-label">الفارق الزمني (Décalage):</span> <span id="decalage-timer-display" style="background:#fee2e2; padding:2px 8px; border-radius:4px; font-family:monospace;">0.0s</span>
                </div>
              </div>
            </div>

            <!-- Filter Controls for 100 Speeches -->
            <div style="background:var(--bg-surface); padding:10px 12px; border-radius:8px; border:1px solid var(--border-line); margin-bottom:12px;">
              <div style="display:grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap:8px; margin-bottom:8px;">
                <!-- Domain Filter -->
                <div>
                  <label id="lbl-domain-filter" style="font-size:10.5px; font-weight:700; color:var(--text-dim); display:block; margin-bottom:2px;">المجال التخصصي:</label>
                  <select id="booth-domain-filter" class="form-control" style="font-size:11.5px; padding:4px 8px; height:32px;" onchange="applyBoothFilters()">
                    <option value="all">🌐 كافة المجالات (10 مجالات دولية)</option>
                    <option value="diplomatic">🌐 القمم الدبلوماسية والأمم المتحدة</option>
                    <option value="energy">⚡ الطاقة والمناخ والغاز والهيدروجين</option>
                    <option value="legal">⚖️ القانون والعقود والتحكيم الدولي</option>
                    <option value="economic">💼 الاقتصاد الكلي والبنوك ودافوس</option>
                    <option value="medical">🩺 الطب الحيوي ومنظمة الصحة العالمية</option>
                    <option value="tech">🤖 الذكاء الاصطناعي والأمن السيبراني</option>
                    <option value="human_rights">🕊️ حقوق الإنسان والعمل الإنساني</option>
                    <option value="logistics">🚢 التجارة الدولية وسلاسل الإمداد</option>
                    <option value="culture">🏛️ اليونسكو والتراث والتنوع اللغوي</option>
                    <option value="geopolitics">🤝 الأمن الإقليمي والجامعة العربية</option>
                    <option value="advanced_drills">📜 ورش الترجمة المؤسساتية التخصصية</option>
                  </select>
                </div>

                <!-- Mode & Tier Filter -->
                <div>
                  <label id="lbl-tier-filter" style="font-size:10.5px; font-weight:700; color:var(--text-dim); display:block; margin-bottom:2px;">نوع الوصول / الباقة:</label>
                  <select id="booth-tier-filter" class="form-control" style="font-size:11.5px; padding:4px 8px; height:32px;" onchange="applyBoothFilters()">
                    <option value="all">الكل (مجاني + باقة ترانز اب)</option>
                    <option value="free">🟢 تدريب مجاني متاح للجميع</option>
                    <option value="paid">👑 باقة ترانز اب (TransUp Pro)</option>
                  </select>
                </div>

                <!-- Language Pair Filter -->
                <div>
                  <label id="lbl-pair-filter" style="font-size:10.5px; font-weight:700; color:var(--text-dim); display:block; margin-bottom:2px;">الزوج اللغوي:</label>
                  <select id="booth-pair-filter" class="form-control" style="font-size:11.5px; padding:4px 8px; height:32px;" onchange="applyBoothFilters()">
                    <option value="all">كافة اللغات (EN, FR, DE, ES, AR, IT)</option>
                    <option value="en_ar">الإنجليزية ➔ العربية</option>
                    <option value="fr_ar">الفرنسية ➔ العربية</option>
                    <option value="de_ar">الألمانية ➔ العربية</option>
                    <option value="ar_en">العربية ➔ الإنجليزية</option>
                    <option value="es_ar">الإسبانية ➔ العربية</option>
                  </select>
                </div>

                <!-- Live Keyword Search -->
                <div>
                  <label id="lbl-search-filter" style="font-size:10.5px; font-weight:700; color:var(--text-dim); display:block; margin-bottom:2px;">بحث سريع في الخطابات:</label>
                  <input type="text" id="booth-search-input" class="form-control" style="font-size:11.5px; padding:4px 8px; height:32px;" placeholder="ابحث بالعنوان أو المتحدث..." oninput="applyBoothFilters()">
                </div>
              </div>
            </div>

            <!-- Speech Selector Dropdown with Prev/Next -->
            <div class="form-group" style="margin-bottom:12px;">
              <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:4px;">
                <label id="lbl-select-speech" style="font-size:12px; font-weight:700; margin:0;">اختر الخطاب التدريبي من القائمة (110 خطابات واقعية طويلة):</label>
                <div style="display:flex; gap:6px;">
                  <button class="nav-pill" style="padding:2px 8px; font-size:11px;" onclick="navigateBoothSpeech(-1)">◀ السابق</button>
                  <button class="nav-pill" style="padding:2px 8px; font-size:11px;" onclick="navigateBoothSpeech(1)">التالي ▶</button>
                </div>
              </div>
              <select id="booth-speech-select" class="form-control" style="font-size:12px; font-weight:600;" onchange="loadSelectedBoothSpeech()">
                <!-- Populated dynamically by JavaScript -->
              </select>
            </div>

            <!-- Speech Player & Metadata Strip -->
            <div style="background:var(--bg-surface); padding:12px; border-radius:8px; border:1px solid var(--border-line); margin-bottom:12px;">
              <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px; margin-bottom:8px;">
                <div>
                  <div style="font-size:13px; font-weight:800; display:flex; align-items:center; gap:6px;">
                    <span id="booth-domain-icon">🌐</span>
                    <span id="booth-speech-title-heading">افتتاح الدورة الثامنة والسبعين للجمعية العامة للأمم المتحدة</span>
                  </div>
                  <div style="font-size:11px; color:var(--text-dim); margin-top:2px;">
                    📢 <span id="booth-speaker-name" style="font-weight:700;">الأمين العام للأمم المتحدة</span> • <span id="booth-lang-pair-badge" style="color:var(--crimson); font-weight:700;">الإنجليزية ➔ العربية</span> • <span id="booth-tier-badge" style="background:#dcfce7; color:#15803d; padding:1px 6px; border-radius:4px; font-size:10px;">🟢 مجاني</span>
                  </div>
                </div>

                <!-- Speech Audio Controls -->
                <div style="display:flex; align-items:center; gap:6px; flex-wrap:wrap;">
                  <button class="btn-primary-red" style="padding:5px 12px; font-size:12px; display:inline-flex; align-items:center; gap:4px;" onclick="playBoothSpeechAudio()">
                    <span>▶</span> <span id="txt-play-audio">تشغيل صوت الخطاب</span>
                  </button>
                  <button class="btn-outline-action" style="padding:5px 10px; font-size:12px;" onclick="stopBoothSpeechAudio()">
                    <span>⏹️</span> <span id="txt-stop-audio">إيقاف</span>
                  </button>
                  <select id="booth-speed-select" class="form-control" style="width:auto; padding:3px 6px; font-size:11px; height:28px;" onchange="updateBoothAudioSpeed()">
                    <option value="0.8">سرعة 0.8x</option>
                    <option value="1.0" selected>سرعة 1.0x</option>
                    <option value="1.2">سرعة 1.2x</option>
                  </select>
                  <!-- Font size adjustments -->
                  <button class="nav-pill" style="padding:3px 6px; font-size:11px;" onclick="adjustBoothFontSize(-1)" title="تصغير الخط">A-</button>
                  <button class="nav-pill" style="padding:3px 6px; font-size:11px;" onclick="adjustBoothFontSize(1)" title="تكبير الخط">A+</button>
                </div>
              </div>

              <!-- Live Recording Simulator -->
              <div style="background:var(--bg-body); padding:8px 12px; border-radius:6px; border:1px dashed var(--border-line); display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:6px;">
                <div style="display:flex; align-items:center; gap:8px;">
                  <button id="btn-toggle-recording" class="nav-pill" style="background:#fee2e2; color:#b91c1c; border-color:#fca5a5; font-size:11.5px; font-weight:700; padding:4px 10px;" onclick="toggleBoothRecording()">
                    🎙️ <span id="txt-record-status">تسجيل صوتي فوري في الكابينة</span>
                  </button>
                  <span id="booth-record-timer" style="font-size:11px; font-family:monospace; font-weight:700; display:none; color:#dc2626;">🔴 يسجل الآن: 00:00</span>
                </div>
                <div id="booth-recording-hint" style="font-size:10.5px; color:var(--text-faint);">
                  محاكاة بيئة الكابينة الاحترافية مع مؤشر الفارق الزمني (Ear-Voice Span)
                </div>
              </div>
            </div>

            <!-- Side-by-Side: Source Script vs Student Translation -->
            <div style="display:grid; grid-template-columns:1fr 1fr; gap:12px; margin-bottom:12px;">
              <div>
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:4px;">
                  <label id="lbl-source-script" style="font-size:11.5px; font-weight:700; color:var(--text-dim); margin:0;">النص المصدر المنطوق في الكابينة:</label>
                  <span id="booth-source-wordcount" style="font-size:10.5px; color:var(--text-faint);">0 كلمة</span>
                </div>
                <div id="booth-source-transcript" style="height:150px; overflow-y:auto; background:var(--bg-surface); padding:12px; border-radius:8px; border:1px solid var(--border-line); font-size:13px; line-height:1.7; transition: font-size 0.2s;">
                  جاري تحميل الخطاب التدريبي...
                </div>
              </div>

              <div>
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:4px;">
                  <label id="lbl-user-transcription" style="font-size:11.5px; font-weight:700; color:var(--text-dim); margin:0;">صياغتك الفورية (التفريغ الشفهي أو الكتابي):</label>
                  <span id="booth-user-wordcount" style="font-size:10.5px; color:var(--text-faint);">0 كلمة</span>
                </div>
                <textarea id="booth-user-transcription" class="form-control" style="height:150px; font-size:12.5px; line-height:1.6;" placeholder="ابدأ الترجمة الفورية فور سماع أول وحدة معنى (Ear-Voice Span: 2-3 ثوانٍ)..." oninput="updateBoothUserWordCount()"></textarea>
              </div>
            </div>

            <!-- Key Terminology Tags -->
            <div id="booth-terminology-box" style="background:var(--bg-surface); padding:8px 12px; border-radius:8px; border:1px solid var(--border-line); margin-bottom:12px;">
              <div style="font-size:11px; font-weight:700; color:var(--text-dim); margin-bottom:4px;">المصطلحات المفتاحية المعتمدة في هذا الخطاب (Terminology Guide):</div>
              <div id="booth-terminology-chips" style="display:flex; flex-wrap:wrap; gap:6px;">
                <!-- Filled dynamically -->
              </div>
            </div>

            <!-- Model Solution Reveal -->
            <div style="display:flex; gap:8px;">
              <button class="btn-outline-action" style="flex:1; font-size:12px; padding:8px;" onclick="toggleBoothModelSolution()">
                🔍 <span id="txt-show-model-solution">إظهار الترجمة المؤسساتية النموذجية المعتمدة</span>
              </button>
              <button class="btn-outline-action" style="padding:8px 12px; font-size:12px;" onclick="sendBoothReportToAdmin()" title="إرسال تقرير التمرين للإدارة">
                ✉️ <span id="txt-send-report">إرسال تقرير التمرين</span>
              </button>
            </div>

            <div id="booth-model-solution" style="display:none; margin-top:10px; background:#ecfdf5; border:1px solid #6ee7b7; color:#065f46; padding:12px; border-radius:8px; font-size:12.5px; line-height:1.7;">
              <strong>الترجمة النموذجية المعتمدة:</strong>
              <div id="booth-model-solution-text" style="margin-top:4px;"></div>
            </div>
          </div>
        </div>

        <!-- STUDENT SUB-SECTION 2: CONSECUTIVE INTERPRETATION & ROZAN NOTEPAD -->
        <div id="student-consec-box" style="display:none;">
          <div style="background:var(--bg-body); padding:16px; border-radius:12px; border:1px solid var(--border-line); margin-bottom:14px;">
            <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap:8px; margin-bottom:10px;">
              <div>
                <h4 id="txt-consec-title" style="font-size:15px; font-weight:800; margin:0 0 4px;">📝 ورشة الترجمة التتابعية ونظام روزان (Jean-François Rozan)</h4>
                <div id="txt-consec-sub" style="font-size:11.5px; color:var(--text-dim);">مبادئ روزان السبعة: التدوين الرأسي (Verticality)، التدرج (Shift)، الروابط المنطقية، ونظام الرموز المختصرة.</div>
              </div>
              <span class="reg-pill" style="font-size:11px;">الترميز العمودي (Verticality)</span>
            </div>

            <div class="form-group" style="margin-bottom:12px;">
              <label id="lbl-consec-select" style="font-size:12px; font-weight:700;">اختر خطاب الترجمة التتابعية (خطابات دبلوماسية واقتصادية متخصصة):</label>
              <select id="consec-speech-select" class="form-control" style="font-size:12px;" onchange="loadSelectedConsecSpeech()">
                <!-- Populated dynamically from BOOTH_100_SPEECHES consecutive items -->
              </select>
            </div>

            <!-- Rozan Quick Symbols Toolbar -->
            <div style="background:var(--bg-surface); padding:8px 12px; border-radius:8px; border:1px solid var(--border-line); margin-bottom:10px;">
              <div id="txt-rozan-symbols-title" style="font-size:11px; font-weight:700; margin-bottom:6px; color:var(--text-dim);">شريط رموز روزان السريعة (انقر للإدراج فوراً في الملاحظات):</div>
              <div style="display:flex; flex-wrap:wrap; gap:6px;">
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('➔')">➔ (نتيجة/يؤدي)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('▲')">▲ (زيادة/صعود)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('▼')">▼ (انخفاض/تراجع)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('≠')">≠ (اختلاف/معارضة)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('§')">§ (فقرة جديدة)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('⏳')">⏳ (زمن/مستقبل)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('★')">★ (أولوية/مهم)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('?')">? (شك/تساؤل)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('∵')">∵ (السبب car/because)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('∴')">∴ (النتيجة donc/therefore)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('Ø')">Ø (نفي/إلغاء)</button>
                <button class="nav-pill" style="padding:2px 8px; font-size:12px;" onclick="insertRozanSymbol('OK')">OK (موافقة/اتفاق)</button>
              </div>
            </div>

            <div style="display:grid; grid-template-columns:1fr 1.2fr; gap:12px; margin-bottom:12px;">
              <div>
                <label id="lbl-consec-source" style="font-size:11.5px; font-weight:700;">الخطاب المصدر للاستماع:</label>
                <div id="consec-source-transcript" style="background:var(--bg-surface); padding:10px; border-radius:8px; border:1px solid var(--border-line); font-size:12.5px; line-height:1.6; height:150px; overflow-y:auto;">
                  جاري تحميل الخطاب التتابعي...
                </div>
                <div style="display:flex; gap:6px; margin-top:6px;">
                  <button class="btn-primary-red" style="flex:1; padding:5px; font-size:11.5px;" onclick="playConsecutiveSpeechAudio()">
                    🔊 <span id="txt-listen-consec">استمع للخطاب بصوت المتحدث</span>
                  </button>
                  <button class="btn-outline-action" style="padding:5px 10px; font-size:11.5px;" onclick="stopConsecutiveSpeechAudio()">
                    ⏹️ إيقاف
                  </button>
                </div>
              </div>

              <div>
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:2px;">
                  <label id="lbl-rozan-pad" style="font-size:11.5px; font-weight:700;">مفكرة روزان التتابعية (Rozan Pad):</label>
                  <button class="nav-pill" style="padding:1px 6px; font-size:10px;" onclick="document.getElementById('rozan-notepad-text').value=''">مسح</button>
                </div>
                <textarea id="rozan-notepad-text" class="form-control" style="height:150px; font-family:'Fira Code', monospace; font-size:12px;" placeholder="دوّن الملاحظات العمودية هنا باستخدام الرموز أعلاه..."></textarea>
              </div>
            </div>

            <!-- Model Solution for Rozan Consecutive -->
            <button class="btn-outline-action" style="width:100%; font-size:12px; padding:8px;" onclick="toggleConsecModelSolution()">
              🔍 <span id="txt-show-consec-model">مقارنة الصياغة مع الترجمة المؤسساتية المعتمدة ونموذج روزان</span>
            </button>
            <div id="consec-model-solution" style="display:none; margin-top:10px; background:#eff6ff; border:1px solid #93c5fd; color:#1e40af; padding:12px; border-radius:8px; font-size:12.5px; line-height:1.7;">
              <strong>الترجمة التتابعية النموذجية:</strong><br>
              <div id="consec-model-solution-text" style="margin:4px 0 8px;"></div>
              <div style="border-top:1px dashed #93c5fd; padding-top:6px; font-size:11px; color:#1e3a8a;">
                <strong>نموذج تدوين رموز روزان المختصر:</strong>
                <span id="consec-rozan-summary-text" style="font-family:monospace; margin-right:6px;"></span>
              </div>
            </div>
          </div>
        </div>

        <!-- STUDENT SUB-SECTION 3: COMPREHENSIVE LEVEL-PLACEMENT EXAM -->"""

    if old_booth_section_pattern.search(html):
        html = old_booth_section_pattern.sub(new_booth_section, html, count=1)
        print("Replaced Booth UI with 100 Speeches Explorer successfully.")
    else:
        print("Warning: old_booth_section_pattern not found, checking alternatives...")

    with open("index.html", "w", encoding="utf-8") as f:
        f.write(html)
    print("Saved index.html after booth section update.")

if __name__ == "__main__":
    main()
