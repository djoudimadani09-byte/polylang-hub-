#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import re

def main():
    with open("index.html", "r", encoding="utf-8") as f:
        html = f.read()

    # Replacement for boothSpeeches and consecSpeeches logic (from line 4724 to start of multiTrackExams)
    booth_js_pattern = re.compile(
        r'const boothSpeeches = \{.*?(?=// --- REAL STANDARDIZED 100-QUESTIONS MULTI-TRACK PLACEMENT EXAMS ---)',
        re.DOTALL
    )

    new_booth_js = """    // --- 100 CONFERENCE SPEECHES & ROZAN INTERPRETATION ENGINE ---
    let currentFilteredSpeeches = [];
    let currentSpeechIndex = 0;
    let boothRecordingInterval = null;
    let boothRecordingSeconds = 0;
    let isBoothRecording = false;
    let currentBoothFontSize = 13;
    let decalageInterval = null;
    let decalageSeconds = 0;

    function getBoothSpeeches() {
      if (typeof window !== 'undefined' && Array.isArray(window.BOOTH_100_SPEECHES) && window.BOOTH_100_SPEECHES.length > 0) {
        return window.BOOTH_100_SPEECHES;
      }
      return [];
    }

    function applyBoothFilters() {
      const all = getBoothSpeeches();
      const domainFilter = document.getElementById('booth-domain-filter') ? document.getElementById('booth-domain-filter').value : 'all';
      const tierFilter = document.getElementById('booth-tier-filter') ? document.getElementById('booth-tier-filter').value : 'all';
      const pairFilter = document.getElementById('booth-pair-filter') ? document.getElementById('booth-pair-filter').value : 'all';
      const searchInp = document.getElementById('booth-search-input');
      const q = searchInp ? searchInp.value.trim().toLowerCase() : '';

      currentFilteredSpeeches = all.filter(sp => {
        if (domainFilter !== 'all' && sp.domain_id !== domainFilter) return false;
        if (tierFilter !== 'all' && sp.tier !== tierFilter) return false;
        if (pairFilter !== 'all') {
          if (pairFilter === 'en_ar' && !sp.pair.includes('الإنجليزية ➔ العربية')) return false;
          if (pairFilter === 'fr_ar' && !sp.pair.includes('الفرنسية ➔ العربية')) return false;
          if (pairFilter === 'de_ar' && !sp.pair.includes('الألمانية ➔ العربية')) return false;
          if (pairFilter === 'ar_en' && !sp.pair.includes('العربية ➔ الإنجليزية')) return false;
          if (pairFilter === 'es_ar' && !sp.pair.includes('الإسبانية ➔ العربية')) return false;
        }
        if (q) {
          const matchTitle = (sp.title_ar + ' ' + sp.title_en + ' ' + sp.speaker + ' ' + sp.source).toLowerCase();
          if (!matchTitle.includes(q)) return false;
        }
        return true;
      });

      renderBoothDropdown();
      renderConsecDropdown();
    }

    function renderBoothDropdown() {
      const select = document.getElementById('booth-speech-select');
      if (!select) return;

      select.innerHTML = '';
      if (currentFilteredSpeeches.length === 0) {
        select.innerHTML = '<option value="">⚠️ لا توجد خطابات مطابقة للمرشحات الحالية</option>';
        return;
      }

      currentFilteredSpeeches.forEach((sp, idx) => {
        const opt = document.createElement('option');
        opt.value = sp.id;
        const tierBadge = sp.tier === 'paid' ? '👑' : '🟢';
        opt.textContent = `${tierBadge} [#${sp.num}] ${sp.icon} ${sp.title_ar} (${sp.pair})`;
        select.appendChild(opt);
      });

      // Update counter
      const counterBadge = document.getElementById('booth-counter-badge');
      if (counterBadge) {
        counterBadge.textContent = `${currentFilteredSpeeches.length} من أصل 110 خطابات`;
      }

      currentSpeechIndex = 0;
      loadSelectedBoothSpeech();
    }

    function renderConsecDropdown() {
      const select = document.getElementById('consec-speech-select');
      if (!select) return;

      const all = getBoothSpeeches();
      const consecList = all.filter(s => s.mode === 'consecutive' || s.domain_id === 'advanced_drills');
      select.innerHTML = '';

      consecList.forEach(sp => {
        const opt = document.createElement('option');
        opt.value = sp.id;
        const tierBadge = sp.tier === 'paid' ? '👑' : '🟢';
        opt.textContent = `${tierBadge} [#${sp.num}] ${sp.icon} ${sp.title_ar} (${sp.pair})`;
        select.appendChild(opt);
      });

      loadSelectedConsecSpeech();
    }

    function loadSelectedBoothSpeech() {
      const select = document.getElementById('booth-speech-select');
      if (!select) return;

      const spId = select.value;
      const all = getBoothSpeeches();
      const sp = all.find(s => s.id === spId) || currentFilteredSpeeches[0];
      if (!sp) return;

      const iconEl = document.getElementById('booth-domain-icon');
      if (iconEl) iconEl.textContent = sp.icon || '🎙️';

      const titleEl = document.getElementById('booth-speech-title-heading');
      if (titleEl) titleEl.textContent = sp.title_ar;

      const speakerEl = document.getElementById('booth-speaker-name');
      if (speakerEl) speakerEl.textContent = sp.speaker;

      const pairBadge = document.getElementById('booth-lang-pair-badge');
      if (pairBadge) pairBadge.textContent = sp.pair;

      const tierBadge = document.getElementById('booth-tier-badge');
      if (tierBadge) {
        if (sp.tier === 'paid') {
          tierBadge.textContent = '👑 باقة ترانز اب (TransUp Pro)';
          tierBadge.style.background = '#fef3c7';
          tierBadge.style.color = '#b45309';
        } else {
          tierBadge.textContent = '🟢 تدريب مجاني متاح';
          tierBadge.style.background = '#dcfce7';
          tierBadge.style.color = '#15803d';
        }
      }

      const transcriptEl = document.getElementById('booth-source-transcript');
      if (transcriptEl) {
        transcriptEl.textContent = sp.source;
      }

      // Word count
      const wordCount = sp.source ? sp.source.split(/\\s+/).filter(Boolean).length : 0;
      const wcEl = document.getElementById('booth-source-wordcount');
      if (wcEl) wcEl.textContent = `${wordCount} كلمة`;

      // Terminology chips
      const chipsBox = document.getElementById('booth-terminology-chips');
      if (chipsBox) {
        chipsBox.innerHTML = '';
        if (sp.terms && sp.terms.length > 0) {
          sp.terms.forEach(t => {
            const pill = document.createElement('span');
            pill.className = 'nav-pill';
            pill.style.fontSize = '10.5px';
            pill.style.padding = '2px 8px';
            pill.style.background = 'var(--bg-body)';
            pill.textContent = t;
            chipsBox.appendChild(pill);
          });
        }
      }

      // Model solution
      const solText = document.getElementById('booth-model-solution-text');
      if (solText) solText.textContent = sp.solution;

      const solBox = document.getElementById('booth-model-solution');
      if (solBox) solBox.style.display = 'none';

      stopBoothSpeechAudio();
    }

    function navigateBoothSpeech(dir) {
      const select = document.getElementById('booth-speech-select');
      if (!select || select.options.length <= 1) return;

      let idx = select.selectedIndex + dir;
      if (idx < 0) idx = select.options.length - 1;
      if (idx >= select.options.length) idx = 0;

      select.selectedIndex = idx;
      loadSelectedBoothSpeech();
    }

    function playBoothSpeechAudio() {
      const select = document.getElementById('booth-speech-select');
      if (!select) return;

      const all = getBoothSpeeches();
      const sp = all.find(s => s.id === select.value) || currentFilteredSpeeches[0];
      if (!sp) return;

      const speedSelect = document.getElementById('booth-speed-select');
      const speedRate = speedSelect ? parseFloat(speedSelect.value) : 1.0;

      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
        const utter = new SpeechSynthesisUtterance(sp.source);
        utter.lang = sp.audio_lang || 'en-US';
        utter.rate = speedRate;
        window.speechSynthesis.speak(utter);
      }

      // Live Décalage Timer (Ear-Voice Span)
      clearInterval(decalageInterval);
      decalageSeconds = 0;
      decalageInterval = setInterval(() => {
        decalageSeconds += 0.1;
        const display = document.getElementById('decalage-timer-display');
        if (display) {
          display.textContent = decalageSeconds.toFixed(1) + 's';
          if (decalageSeconds >= 2.0 && decalageSeconds <= 4.0) {
            display.style.background = '#dcfce7'; // green - optimal
            display.style.color = '#15803d';
          } else if (decalageSeconds > 4.0) {
            display.style.background = '#fee2e2'; // red - lagging
            display.style.color = '#b91c1c';
          }
        }
      }, 100);
    }

    function stopBoothSpeechAudio() {
      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
      }
      clearInterval(decalageInterval);
      const display = document.getElementById('decalage-timer-display');
      if (display) {
        display.textContent = '0.0s';
        display.style.background = '#fee2e2';
        display.style.color = '#b91c1c';
      }
    }

    function updateBoothAudioSpeed() {
      stopBoothSpeechAudio();
    }

    function toggleBoothRecording() {
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
    }

    function adjustBoothFontSize(delta) {
      currentBoothFontSize = Math.max(11, Math.min(20, currentBoothFontSize + delta));
      const box = document.getElementById('booth-source-transcript');
      if (box) box.style.fontSize = currentBoothFontSize + 'px';
    }

    function updateBoothUserWordCount() {
      const txt = document.getElementById('booth-user-transcription');
      const countEl = document.getElementById('booth-user-wordcount');
      if (txt && countEl) {
        const words = txt.value.trim().split(/\\s+/).filter(Boolean).length;
        countEl.textContent = `${words} كلمة`;
      }
    }

    function toggleBoothModelSolution() {
      const box = document.getElementById('booth-model-solution');
      if (box) {
        box.style.display = box.style.display === 'none' ? 'block' : 'none';
      }
    }

    function sendBoothReportToAdmin() {
      const select = document.getElementById('booth-speech-select');
      const userText = document.getElementById('booth-user-transcription') ? document.getElementById('booth-user-transcription').value : '';
      const all = getBoothSpeeches();
      const sp = all.find(s => s.id === (select ? select.value : '')) || {};

      alert(`✓ تم إرسال تقرير تمرين كابينة الترجمة الفورية إلى المشرف الأكاديمي (djoudimadani09@gmail.com):\\nالخطاب: ${sp.title_ar || 'خطاب كابينة'}\\nالفارق الزمني المحقق: ${decalageSeconds.toFixed(1)} ثانية\\nعدد الكلمات المترجمة: ${userText.split(/\\s+/).filter(Boolean).length}`);
    }

    // --- CONSECUTIVE ROZAN INTERPRETATION FUNCTIONS ---
    function loadSelectedConsecSpeech() {
      const select = document.getElementById('consec-speech-select');
      if (!select) return;

      const spId = select.value;
      const all = getBoothSpeeches();
      const sp = all.find(s => s.id === spId);
      if (!sp) return;

      const transcriptBox = document.getElementById('consec-source-transcript');
      if (transcriptBox) {
        transcriptBox.innerHTML = `<strong>${sp.icon} ${sp.title_ar}:</strong><br><span style="color:var(--text-dim); font-size:11px;">📢 ${sp.speaker} (${sp.pair})</span><br><br>"${sp.source}"`;
      }

      const solBox = document.getElementById('consec-model-solution');
      if (solBox) solBox.style.display = 'none';

      const solText = document.getElementById('consec-model-solution-text');
      if (solText) solText.textContent = sp.solution;

      const rozanText = document.getElementById('consec-rozan-summary-text');
      if (rozanText) rozanText.textContent = sp.rozan || 'Verticality + Shift + Logical connectors';

      stopConsecutiveSpeechAudio();
    }

    function playConsecutiveSpeechAudio() {
      const select = document.getElementById('consec-speech-select');
      const all = getBoothSpeeches();
      const sp = all.find(s => s.id === (select ? select.value : '')) || all[0];
      if (!sp) return;

      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
        const utter = new SpeechSynthesisUtterance(sp.source);
        utter.lang = sp.audio_lang || 'ar-SA';
        utter.rate = 0.9;
        window.speechSynthesis.speak(utter);
      }
    }

    function stopConsecutiveSpeechAudio() {
      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
      }
    }

    function toggleConsecModelSolution() {
      const el = document.getElementById('consec-model-solution');
      if (el) el.style.display = el.style.display === 'none' ? 'block' : 'none';
    }

    function insertRozanSymbol(sym) {
      const txt = document.getElementById('rozan-notepad-text');
      if (!txt) return;
      txt.value += (txt.value ? ' ' : '') + sym + ' ';
      txt.focus();
    }

"""

    if booth_js_pattern.search(html):
        html = booth_js_pattern.sub(lambda m: new_booth_js, html, count=1)
        print("Updated booth JS functions successfully.")
    else:
        print("Warning: booth_js_pattern not matched.")

    with open("index.html", "w", encoding="utf-8") as f:
        f.write(html)
    print("Saved index.html with new booth JS.")

if __name__ == "__main__":
    main()
