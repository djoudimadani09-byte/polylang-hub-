// build_update.js - Re-engineer Polylang Hub single-file application
const fs = require('fs');

console.log('Reading index.html...');
let html = fs.readFileSync('index.html', 'utf8');

// =========================================================================
// 1. CSS ENHANCEMENTS: Media Hub, Backend API Status, Audio Player, Mobile
// =========================================================================
const mediaHubCSS = `
    /* -------------------------------------------------------------
       BACKEND NESTJS API STATUS PILL & MODAL
       ------------------------------------------------------------- */
    .backend-api-pill {
      display: inline-flex;
      align-items: center;
      gap: 0.45rem;
      background: var(--bg-surface-secondary);
      border: 1.5px solid var(--border-main);
      padding: 0.35rem 0.75rem;
      font-size: 0.78rem;
      font-weight: 700;
      border-radius: 50px;
      cursor: pointer;
      transition: all var(--transition-fast);
      color: var(--text-main);
    }
    .backend-api-pill:hover {
      border-color: var(--primary);
      transform: translateY(-1px);
    }
    .backend-pulse-dot {
      width: 8px;
      height: 8px;
      border-radius: 50%;
      background: #10b981;
      box-shadow: 0 0 8px #10b981;
      animation: backendPulse 2s infinite;
    }
    @keyframes backendPulse {
      0%, 100% { transform: scale(1); opacity: 1; }
      50% { transform: scale(1.3); opacity: 0.6; }
    }

    /* -------------------------------------------------------------
       TRAINING & MEDIA CENTER STYLING (VIDEOS & AUDIO HUB)
       ------------------------------------------------------------- */
    .media-center-hero {
      background: linear-gradient(135deg, rgba(229, 57, 53, 0.08) 0%, rgba(30, 41, 59, 0.05) 100%);
      border: 1.5px solid var(--border-main);
      border-radius: var(--radius-lg);
      padding: 1.75rem;
      margin-bottom: 1.5rem;
      position: relative;
      overflow: hidden;
    }
    .media-category-tabs {
      display: flex;
      gap: 0.5rem;
      overflow-x: auto;
      padding-bottom: 0.5rem;
      margin-bottom: 1.25rem;
      scrollbar-width: none;
    }
    .media-category-tabs::-webkit-scrollbar {
      display: none;
    }
    .media-tab-btn {
      background: var(--bg-surface);
      border: 1.5px solid var(--border-main);
      color: var(--text-muted);
      padding: 0.5rem 1.1rem;
      border-radius: 50px;
      font-size: 0.85rem;
      font-weight: 700;
      cursor: pointer;
      white-space: nowrap;
      transition: all var(--transition-fast);
    }
    .media-tab-btn:hover {
      border-color: var(--primary);
      color: var(--primary);
    }
    .media-tab-btn.active {
      background: var(--primary);
      color: #ffffff;
      border-color: var(--primary);
      box-shadow: 0 4px 12px rgba(229, 57, 53, 0.25);
    }

    /* Video Player Layout */
    .video-main-wrapper {
      position: relative;
      width: 100%;
      padding-top: 56.25%; /* 16:9 Aspect Ratio */
      border-radius: var(--radius-md);
      overflow: hidden;
      background: #000000;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
      border: 1.5px solid var(--border-main);
    }
    .video-main-wrapper iframe {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border: none;
    }
    .video-playlist-item {
      display: flex;
      gap: 0.85rem;
      padding: 0.85rem;
      border-radius: var(--radius-md);
      background: var(--bg-surface);
      border: 1.5px solid var(--border-main);
      cursor: pointer;
      transition: all var(--transition-fast);
      align-items: center;
      margin-bottom: 0.65rem;
    }
    .video-playlist-item:hover {
      border-color: var(--primary);
      transform: translateX(-2px);
    }
    [dir="ltr"] .video-playlist-item:hover {
      transform: translateX(2px);
    }
    .video-playlist-item.active {
      border-color: var(--primary);
      background: rgba(229, 57, 53, 0.05);
      box-shadow: 0 2px 8px rgba(229, 57, 53, 0.12);
    }
    .video-thumb-icon {
      width: 52px;
      height: 52px;
      border-radius: var(--radius-sm);
      background: linear-gradient(135deg, var(--primary) 0%, #b71c1c 100%);
      color: #ffffff;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.4rem;
      flex-shrink: 0;
    }

    /* Interactive Audio Hub & Player */
    .audio-player-card {
      background: var(--bg-surface);
      border: 1.5px solid var(--border-main);
      border-radius: var(--radius-lg);
      padding: 1.5rem;
      margin-bottom: 1.25rem;
      box-shadow: 0 4px 16px rgba(0,0,0,0.04);
      position: relative;
    }
    .audio-waveform-visualizer {
      display: flex;
      align-items: flex-end;
      gap: 4px;
      height: 48px;
      padding: 4px 8px;
      background: var(--bg-surface-secondary);
      border-radius: var(--radius-sm);
      margin: 1rem 0;
      overflow: hidden;
    }
    .eq-bar {
      flex: 1;
      background: var(--primary);
      border-radius: 2px 2px 0 0;
      height: 15%;
      transition: height 0.15s ease;
    }
    .audio-player-card.playing .eq-bar:nth-child(2n) {
      animation: eqBounce1 0.6s infinite alternate ease-in-out;
    }
    .audio-player-card.playing .eq-bar:nth-child(2n+1) {
      animation: eqBounce2 0.8s infinite alternate ease-in-out;
    }
    .audio-player-card.playing .eq-bar:nth-child(3n) {
      animation: eqBounce3 0.5s infinite alternate ease-in-out;
    }
    @keyframes eqBounce1 { 0% { height: 15%; } 100% { height: 85%; } }
    @keyframes eqBounce2 { 0% { height: 25%; } 100% { height: 100%; } }
    @keyframes eqBounce3 { 0% { height: 10%; } 100% { height: 60%; } }

    .audio-controls-row {
      display: flex;
      align-items: center;
      justify-content: space-between;
      gap: 1rem;
      flex-wrap: wrap;
    }
    .audio-play-btn {
      width: 54px;
      height: 54px;
      border-radius: 50%;
      background: var(--primary);
      color: #ffffff;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.5rem;
      border: none;
      cursor: pointer;
      box-shadow: 0 4px 14px rgba(229, 57, 53, 0.35);
      transition: all var(--transition-fast);
      flex-shrink: 0;
    }
    .audio-play-btn:hover {
      transform: scale(1.06);
      background: #d32f2f;
    }
    .audio-seek-slider {
      flex: 1;
      min-width: 140px;
      accent-color: var(--primary);
      cursor: pointer;
      height: 6px;
    }
    .audio-speed-chips {
      display: flex;
      gap: 0.35rem;
    }
    .speed-chip {
      padding: 0.25rem 0.55rem;
      border-radius: var(--radius-sm);
      border: 1px solid var(--border-main);
      background: var(--bg-surface-secondary);
      font-size: 0.76rem;
      font-weight: 700;
      cursor: pointer;
      color: var(--text-muted);
    }
    .speed-chip.active {
      background: var(--primary);
      color: #ffffff;
      border-color: var(--primary);
    }
    .audio-transcript-box {
      background: var(--bg-surface-secondary);
      border: 1px solid var(--border-main);
      border-radius: var(--radius-md);
      padding: 1rem;
      margin-top: 1rem;
      font-size: 0.88rem;
      line-height: 1.7;
      max-height: 180px;
      overflow-y: auto;
    }
    .transcript-phrase {
      padding: 2px 4px;
      border-radius: 3px;
      transition: background 0.2s;
    }
    .transcript-phrase.active {
      background: rgba(229, 57, 53, 0.15);
      color: var(--primary-dark);
      font-weight: 800;
    }

    /* Breathing rhythm circle for interpreter mental recovery */
    .breathe-circle-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 1.5rem;
      text-align: center;
    }
    .breathe-circle {
      width: 120px;
      height: 120px;
      border-radius: 50%;
      background: radial-gradient(circle, rgba(16, 185, 129, 0.25) 0%, rgba(37, 99, 235, 0.1) 70%);
      border: 3px solid #10b981;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 900;
      color: #047857;
      margin-bottom: 0.85rem;
      transition: transform 4s ease-in-out;
    }
    .breathe-circle.inhale {
      transform: scale(1.35);
      background: radial-gradient(circle, rgba(16, 185, 129, 0.45) 0%, rgba(37, 99, 235, 0.2) 70%);
    }
    .breathe-circle.hold {
      border-color: #3b82f6;
      color: #1d4ed8;
    }
    .breathe-circle.exhale {
      transform: scale(0.9);
      background: radial-gradient(circle, rgba(16, 185, 129, 0.1) 0%, rgba(37, 99, 235, 0.05) 70%);
    }

    /* Strict Mobile Viewport Enhancements */
    @media (max-width: 768px) {
      .audio-controls-row {
        flex-direction: column;
        align-items: stretch;
      }
      .audio-controls-main {
        display: flex;
        align-items: center;
        gap: 0.75rem;
      }
      .media-center-hero {
        padding: 1rem;
      }
    }
`;

// Insert the CSS right before </style>
if (!html.includes('.backend-api-pill')) {
  html = html.replace('</style>', `${mediaHubCSS}\n  </style>`);
  console.log('✓ Inserted Media Hub and Backend CSS');
}

// =========================================================================
// 2. HEADER ENHANCEMENTS: Backend Pill, Role Switcher Chips with Media
// =========================================================================
const backendPillHTML = `
        <!-- NestJS Backend API Status Pill -->
        <button class="backend-api-pill" id="backendStatusPill" onclick="openBackendConfigModal()" title="NestJS API Connection Status">
          <span class="backend-pulse-dot" id="backendStatusDot"></span>
          <span id="backendStatusText" data-i18n="backendStatusLive">NestJS API Online</span>
        </button>
`;

if (!html.includes('id="backendStatusPill"')) {
  html = html.replace('<div class="lang-dropdown-wrapper"', `${backendPillHTML}\n        <div class="lang-dropdown-wrapper"`);
  console.log('✓ Inserted Backend API Pill in Header');
}

// Add Media Hub Role Chip
const mediaRoleChipHTML = `
        <button class="role-chip" id="chip-media" onclick="switchActiveRole('media')">
          🎬 <span data-i18n="roleChipMedia">مركز التدريب والوسائط (Media Hub)</span>
        </button>
`;

if (!html.includes('id="chip-media"')) {
  html = html.replace('id="chip-enthusiast">', 'id="chip-enthusiast">\n          💡 <span data-i18n="roleChipEnthusiast">محب للغات (Enthusiast)</span>\n        </button>' + mediaRoleChipHTML + '      <!-- end chips -->');
  html = html.replace('<button class="role-chip" id="chip-enthusiast">\n          💡 محب للغات (Enthusiast)\n        </button>', '<button class="role-chip" id="chip-enthusiast" onclick="switchActiveRole(\'enthusiast\')">\n          💡 <span data-i18n="roleChipEnthusiast">محب للغات (Enthusiast)</span>\n        </button>' + mediaRoleChipHTML);
  console.log('✓ Inserted Media Hub Role Chip');
}

// =========================================================================
// 3. MEDIA CENTER HTML SECTION (roleView-media)
// =========================================================================
const mediaViewHTML = `
    <!-- ===========================================================
         ROLE 5 / DEDICATED HUB: TRAINING & MEDIA CENTER (مركز التدريب والوسائط)
         =========================================================== -->
    <div id="roleView-media" class="role-view-section" style="display: none;">
      <!-- Hero Banner -->
      <div class="media-center-hero">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: 1rem;">
          <div>
            <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.35rem;">
              <span style="font-size: 1.5rem;">🎬</span>
              <h2 style="font-size: 1.45rem; font-weight: 900; color: var(--primary-dark);" data-i18n="mediaHeroTitle">
                مركز التدريب والوسائط المتعددة (Training & Media Center)
              </h2>
              <span class="cert-stamp-badge" style="background: rgba(229, 57, 53, 0.1); color: var(--primary); border-color: var(--primary); font-size: 0.75rem;">
                ISO 17100:2015 Accredited
              </span>
            </div>
            <p style="font-size: 0.88rem; color: var(--text-muted); max-width: 820px; line-height: 1.6;" data-i18n="mediaHeroDesc">
              أكاديمية تدريبية متقدمة للمترجمين المحلفين وطلبة الترجمة الفورية، تقدم محاضرات فيديو معتمدة لكبائن الأمم المتحدة، التوطين السمعي البصري (AVT)، وتدريبات صوتية تفاعلية لتقنية التظليل اللغوي (Speech Shadowing) مع إعادة ضبط التركيز الذهني.
            </p>
          </div>
          <div style="display: flex; gap: 0.5rem; flex-wrap: wrap;">
            <button class="btn btn-primary btn-sm" onclick="openBoothSimulationModal()">
              🎙️ <span data-i18n="btnOpenSimBooth">فتح كابينة المحاكاة الفورية</span>
            </button>
            <button class="btn btn-outline btn-sm" onclick="pushToast('✓ تم تحديث مكتبة الوسائط من خادم Render بنجاح')">
              🔄 <span data-i18n="btnSyncMedia">تحديث المحتوى</span>
            </button>
          </div>
        </div>

        <!-- Filter Category Tabs -->
        <div class="media-category-tabs" style="margin-top: 1.25rem;">
          <button class="media-tab-btn active" onclick="filterMediaCategory('all', this)" data-i18n="tabAllMedia">🎬 جميع الوسائط (All Media)</button>
          <button class="media-tab-btn" onclick="filterMediaCategory('video', this)" data-i18n="tabVideoMasterclasses">📺 ماستركلاس الفيديو (Video Masterclasses)</button>
          <button class="media-tab-btn" onclick="filterMediaCategory('audio', this)" data-i18n="tabAudioShadowing">🎧 استوديو التظليل الصوتي (Audio Shadowing)</button>
          <button class="media-tab-btn" onclick="filterMediaCategory('cognitive', this)" data-i18n="tabCognitiveReset">🧠 التحمل الذهني والتركيز (Cognitive Reset)</button>
        </div>
      </div>

      <!-- Video Masterclass Hub Grid -->
      <div id="mediaCategoryVideos">
        <div class="grid-3" style="margin-bottom: 1.5rem;">
          <div class="card" style="grid-column: span 2;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.85rem; flex-wrap: wrap; gap: 0.5rem;">
              <div>
                <span class="history-pill-tag" id="activeVideoTag" style="background: rgba(37, 99, 235, 0.1); color: #1d4ed8; border-color: #3b82f6;">🎙️ كابينة الأمم المتحدة</span>
                <h3 style="font-size: 1.15rem; font-weight: 900; margin-top: 0.25rem;" id="activeVideoTitle">
                  محاضرة كابينة الترجمة الفورية: إدارة الفارق الزمني (Décalage) وضبط الضغط
                </h3>
              </div>
              <span style="font-size: 0.8rem; color: var(--text-muted);" id="activeVideoDuration">⏱️ المدة: 14:20 دقيقة</span>
            </div>

            <!-- Responsive 16:9 Video Embed -->
            <div class="video-main-wrapper">
              <iframe id="mainVideoPlayerIframe" src="https://www.youtube-nocookie.com/embed/uT_hFzV1s54?enablejsapi=1" title="Polylang Masterclass Player" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </div>

            <!-- Video Lesson Takeaways -->
            <div style="margin-top: 1rem; background: var(--bg-surface-secondary); border: 1px solid var(--border-main); border-radius: var(--radius-md); padding: 1rem;">
              <h4 style="font-size: 0.92rem; font-weight: 800; margin-bottom: 0.4rem; color: var(--primary-dark);" data-i18n="videoTakeawaysHeading">
                📌 أهم محاور ومخرجات الدرس التدريبي (Key Learning Takeaways):
              </h4>
              <ul style="font-size: 0.82rem; color: var(--text-muted); line-height: 1.6; padding-inline-start: 1.2rem;" id="activeVideoTakeawaysList">
                <li>التحكم في الفارق الزمني (Décalage / Ear-Voice Span) بمعدل 2 إلى 4 ثوانٍ دون فقدان ترابط السياق.</li>
                <li>تقسيم الجمل المركبة واستخدام استراتيجية التجزئة الدلالية (Semantic Chunking) تحت ضغط المؤتمرات.</li>
                <li>بروتوكول التناوب الثنائي في الكابينة كل 30 دقيقة وفق معايير منظمة AIIC العالمية.</li>
              </ul>
              <div style="display: flex; gap: 0.5rem; margin-top: 0.85rem; flex-wrap: wrap;">
                <button class="btn btn-primary btn-sm" onclick="openBoothSimulationModal()">
                  🎙️ <span data-i18n="btnPracticeLive">بدء تدريب فوري في الكابينة</span>
                </button>
                <button class="btn btn-outline btn-sm" onclick="pushToast('✓ تم تنزيل ملخص الدرس وصيغة SRT للمصطلحات')">
                  📥 <span data-i18n="btnDownloadNotes">تنزيل ملخص الدرس والمصطلحات (PDF)</span>
                </button>
              </div>
            </div>
          </div>

          <!-- Video Playlist Selector -->
          <div class="card">
            <h3 style="font-weight: 900; font-size: 1.05rem; margin-bottom: 0.85rem;" data-i18n="videoPlaylistTitle">
              قائمة المحاضرات التدريبية (Masterclasses)
            </h3>
            <div id="videoPlaylistContainer">
              <div class="video-playlist-item active" onclick="switchMediaVideo(0)">
                <div class="video-thumb-icon">🎙️</div>
                <div>
                  <strong style="font-size: 0.84rem; display: block; line-height: 1.3;">كابينة الترجمة الفورية بالأمم المتحدة</strong>
                  <span style="font-size: 0.74rem; color: var(--text-muted);">14:20 دقيقة • تقنيات Décalage</span>
                </div>
              </div>
              <div class="video-playlist-item" onclick="switchMediaVideo(1)">
                <div class="video-thumb-icon" style="background: linear-gradient(135deg, #2563eb 0%, #1e40af 100%);">🎬</div>
                <div>
                  <strong style="font-size: 0.84rem; display: block; line-height: 1.3;">التوطين السمعي البصري وهندسة SRT</strong>
                  <span style="font-size: 0.74rem; color: var(--text-muted);">18:45 دقيقة • معايير 17 CPS</span>
                </div>
              </div>
              <div class="video-playlist-item" onclick="switchMediaVideo(2)">
                <div class="video-thumb-icon" style="background: linear-gradient(135deg, #059669 0%, #065f46 100%);">⚖️</div>
                <div>
                  <strong style="font-size: 0.84rem; display: block; line-height: 1.3;">الترجمة القضائية والمحلفة (ISO 17100)</strong>
                  <span style="font-size: 0.74rem; color: var(--text-muted);">22:10 دقيقة • المصادقة والـ Apostille</span>
                </div>
              </div>
              <div class="video-playlist-item" onclick="switchMediaVideo(3)">
                <div class="video-thumb-icon" style="background: linear-gradient(135deg, #d97706 0%, #b45309 100%);">📝</div>
                <div>
                  <strong style="font-size: 0.84rem; display: block; line-height: 1.3;">تدوين روزان للترجمة التتبعية (Rozan)</strong>
                  <span style="font-size: 0.74rem; color: var(--text-muted);">16:30 دقيقة • الترميز العمودي</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Interactive Audio Hub (Shadowing Drills & Cognitive Reset) -->
      <div id="mediaCategoryAudio">
        <div style="margin-bottom: 1rem;">
          <h3 style="font-size: 1.15rem; font-weight: 900; color: var(--primary-dark);" data-i18n="audioHubHeading">
            🎧 مختبر التمارين الصوتية التفاعلية والتظليل السريع (Interactive Audio Hub)
          </h3>
          <p style="font-size: 0.82rem; color: var(--text-muted);" data-i18n="audioHubSub">
            استمع وتدرّب مع تسجيلات صوتية عالية الدقة مصممة خصيصاً لاختبار سرعة الاستجابة اللفظية، مع إمكانية تسريع/إبطاء الصوت، وتظليل النص المتزامن.
          </p>
        </div>

        <div class="grid-2">
          <!-- Audio Drill 1: UN General Assembly Shadowing (AR ⇄ EN) -->
          <div class="audio-player-card" id="audioCard1">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.6rem;">
              <span class="history-pill-tag" style="background: rgba(229, 57, 53, 0.1); color: var(--primary); border-color: var(--primary);">
                🎙️ تدريب تظليل شفهي (Shadowing Drill)
              </span>
              <span style="font-size: 0.78rem; color: var(--text-muted); font-weight: 700;">سرعة: 140 WPM</span>
            </div>

            <h4 style="font-weight: 900; font-size: 1rem; margin-bottom: 0.25rem;">
              خطاب الجمعية العامة للأمم المتحدة: تغير المناخ والاستدامة
            </h4>
            <p style="font-size: 0.78rem; color: var(--text-muted);">
              الزوج اللغوي: الإنجليزية ➔ العربية | المتحدث: د. سفيان معمري
            </p>

            <!-- Dynamic Equalizer Bar Canvas -->
            <div class="audio-waveform-visualizer">
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div>
            </div>

            <!-- Controls Row -->
            <div class="audio-controls-row">
              <div style="display: flex; align-items: center; gap: 0.75rem; flex: 1;">
                <button class="audio-play-btn" id="audioBtnPlay1" onclick="toggleAudioTrack(1)" title="تشغيل / إيقاف">
                  ▶
                </button>
                <div style="flex: 1;">
                  <div style="display: flex; justify-content: space-between; font-size: 0.76rem; color: var(--text-muted); margin-bottom: 0.25rem;">
                    <span id="audioTimeCurrent1">00:00</span>
                    <span id="audioTimeTotal1">02:30</span>
                  </div>
                  <input type="range" class="audio-seek-slider" id="audioSeek1" min="0" max="150" value="0" oninput="seekAudioTrack(1, this.value)">
                </div>
              </div>

              <!-- Speed Multiplier -->
              <div class="audio-speed-chips">
                <button class="speed-chip" onclick="setAudioSpeed(1, 0.75, this)">0.75x</button>
                <button class="speed-chip active" onclick="setAudioSpeed(1, 1.0, this)">1.0x</button>
                <button class="speed-chip" onclick="setAudioSpeed(1, 1.25, this)">1.25x</button>
                <button class="speed-chip" onclick="setAudioSpeed(1, 1.5, this)">1.5x</button>
              </div>
            </div>

            <!-- Synchronized Transcript -->
            <div class="audio-transcript-box" id="audioTranscriptBox1">
              <p><span class="transcript-phrase" id="tr1_p1">"Mr. President, distinguished delegates of the General Assembly..."</span></p>
              <p><span class="transcript-phrase" id="tr1_p2">"السيد الرئيس، أصحاب السعادة المندوبين الموقرين في الجمعية العامة للأمم المتحدة..."</span></p>
              <p><span class="transcript-phrase" id="tr1_p3">"We convene today at a pivotal juncture where international climate obligations demand swift legislative action."</span></p>
              <p><span class="transcript-phrase" id="tr1_p4">"نجتمع اليوم في منعطف حاسم تتطلب فيه الالتزامات المناخية الدولية إجراءات تشريعية عاجلة وفورية."</span></p>
            </div>
          </div>

          <!-- Audio Drill 2: Courtroom Sworn Terminology Accent Lab (FR ⇄ ES) -->
          <div class="audio-player-card" id="audioCard2">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.6rem;">
              <span class="history-pill-tag" style="background: rgba(5, 150, 105, 0.1); color: #047857; border-color: #10b981;">
                ⚖️ مختبر النطق والمصطلحات القضائية
              </span>
              <span style="font-size: 0.78rem; color: var(--text-muted); font-weight: 700;">دقة صوتية Phonetics</span>
            </div>

            <h4 style="font-weight: 900; font-size: 1rem; margin-bottom: 0.25rem;">
              صيغ اليمين القضائية والتحكيم الدولي (Tribunal & Arbitrage)
            </h4>
            <p style="font-size: 0.78rem; color: var(--text-muted);">
              الزوج اللغوي: الفرنسية ➔ الإسبانية | مراجعة المخارج الصوتية للمحلفين
            </p>

            <div class="audio-waveform-visualizer">
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
              <div class="eq-bar"></div><div class="eq-bar"></div><div class="eq-bar"></div>
            </div>

            <div class="audio-controls-row">
              <div style="display: flex; align-items: center; gap: 0.75rem; flex: 1;">
                <button class="audio-play-btn" id="audioBtnPlay2" onclick="toggleAudioTrack(2)" title="تشغيل / إيقاف" style="background: #059669;">
                  ▶
                </button>
                <div style="flex: 1;">
                  <div style="display: flex; justify-content: space-between; font-size: 0.76rem; color: var(--text-muted); margin-bottom: 0.25rem;">
                    <span id="audioTimeCurrent2">00:00</span>
                    <span id="audioTimeTotal2">01:50</span>
                  </div>
                  <input type="range" class="audio-seek-slider" id="audioSeek2" min="0" max="110" value="0" oninput="seekAudioTrack(2, this.value)">
                </div>
              </div>

              <div class="audio-speed-chips">
                <button class="speed-chip" onclick="setAudioSpeed(2, 0.75, this)">0.75x</button>
                <button class="speed-chip active" onclick="setAudioSpeed(2, 1.0, this)">1.0x</button>
                <button class="speed-chip" onclick="setAudioSpeed(2, 1.25, this)">1.25x</button>
              </div>
            </div>

            <div class="audio-transcript-box">
              <p><strong>FR:</strong> "Je jure d'accomplir fidèlement ma mission de traduction assermentée..."</p>
              <p><strong>ES:</strong> "Juro cumplir fielmente mi misión de traducción jurada conforme a la ley..."</p>
              <p style="color: var(--text-muted); font-size: 0.78rem; margin-top: 0.4rem;">
                💡 ملاحظة المترجم: ركّز على نبرة الكلمات القانونية المشددة مثل "Fidèlement" و "Jurada".
              </p>
            </div>
          </div>
        </div>

        <!-- Audio 3 / Cognitive Training & Respiration -->
        <div class="card" style="margin-top: 1.25rem;">
          <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 0.85rem;">
            <div style="flex: 1; min-width: 280px;">
              <span class="history-pill-tag" style="background: rgba(37, 99, 235, 0.1); color: #2563eb; border-color: #3b82f6;">
                🧠 تمرين التركيز العصبي للمترجم الشفهي (Alpha 432Hz Soundscape)
              </span>
              <h3 style="font-weight: 900; font-size: 1.1rem; margin: 0.35rem 0;">
                إعادة ضبط التنفس وموجات ألفا بين جولات الكابينة (30-Minute Rotation Reset)
              </h3>
              <p style="font-size: 0.82rem; color: var(--text-muted); line-height: 1.6;">
                نغمة صوتية هارمونية عيار 432Hz تخفض معدل ضربات القلب وتقلل الإجهاد الذهني الناتج عن المعالجة الفورية المزدوجة للغات. اضغط على الزر واتبع حركة دائرة التنفس المتزامنة (شهيق 4 ثوانٍ، حبس 4 ثوانٍ، زفير 6 ثوانٍ).
              </p>
              <div style="display: flex; gap: 0.5rem; margin-top: 0.85rem; align-items: center; flex-wrap: wrap;">
                <button class="btn btn-primary btn-sm" id="btnAlphaWavePlay" onclick="toggleAlphaWavePlayer()">
                  ▶ <span id="lblAlphaWaveBtnText">تشغيل موجات التركيز (432Hz)</span>
                </button>
                <span id="alphaWaveStatusText" style="font-size: 0.78rem; color: var(--text-muted); font-weight: 700;">متوقف</span>
              </div>
            </div>

            <!-- Breathing Visualizer Circle -->
            <div class="breathe-circle-container">
              <div class="breathe-circle" id="breatheCircleElement">
                شهيق
              </div>
              <span style="font-size: 0.76rem; color: var(--text-muted); font-weight: 700;" id="breathePhaseLabel">
                دورة التنفس المتوازن
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
`;

if (!html.includes('id="roleView-media"')) {
  html = html.replace('</main>', `${mediaViewHTML}\n  </main>`);
  console.log('✓ Inserted roleView-media section in main');
}

// =========================================================================
// 4. NESTJS BACKEND CONFIGURATION MODAL
// =========================================================================
const backendModalHTML = `
  <!-- =============================================================
       NESTJS BACKEND API CONFIGURATION & JWT TOKEN MODAL
       ============================================================= -->
  <div class="modal-backdrop" id="backendConfigModalBackdrop" style="display: none;" onclick="handleModalOutsideClick(event, 'backendConfigModalBackdrop')">
    <div class="modal-card" style="max-width: 540px;">
      <div class="modal-header">
        <div style="display: flex; align-items: center; gap: 0.5rem;">
          <span style="font-size: 1.4rem;">⚙️</span>
          <div>
            <h3 style="font-weight: 900; font-size: 1.15rem; color: var(--primary-dark);">إعدادات ربط خادم NestJS API السحابي</h3>
            <p style="font-size: 0.78rem; color: var(--text-muted);">Render Backend & PostgreSQL Live Connection</p>
          </div>
        </div>
        <button class="close-modal-btn" onclick="closeBackendConfigModal()">✕</button>
      </div>

      <div style="padding: 1.25rem;">
        <div style="background: var(--bg-surface-secondary); border: 1.5px solid var(--border-main); border-radius: var(--radius-md); padding: 0.85rem 1rem; margin-bottom: 1.15rem;">
          <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 0.5rem;">
            <span style="font-size: 0.82rem; color: var(--text-muted); font-weight: 700;">حالة الاتصال المباشر:</span>
            <span class="history-pill-tag" id="backendModalLiveBadge" style="background: rgba(16,185,129,0.12); color:#047857; border-color:#10b981;">
              🟢 متصل بخادم Render
            </span>
          </div>
          <div style="font-size: 0.78rem; color: var(--text-muted); word-break: break-all;">
            <strong>نقطة النهاية الحالية:</strong> <span id="currentApiEndpointLabel">https://polylang-hub-backend.onrender.com/api/v1</span>
          </div>
        </div>

        <div class="form-group" style="margin-bottom: 1rem;">
          <label class="form-label" style="font-size: 0.84rem; font-weight: 800;">رابط الخادم المخصص (Backend API Base URL):</label>
          <input type="text" class="form-control" id="customBackendUrlInput" value="https://polylang-hub-backend.onrender.com/api/v1">
          <small style="font-size: 0.74rem; color: var(--text-muted); display: block; margin-top: 0.25rem;">
            يمكنك تغييره إلى رابط محلي (http://localhost:3000/api/v1) أثناء التطوير.
          </small>
        </div>

        <div class="form-group" style="margin-bottom: 1rem;">
          <label class="form-label" style="font-size: 0.84rem; font-weight: 800;">رمز المصادقة الحالي (JWT Bearer Token):</label>
          <textarea class="form-control" id="jwtTokenDisplayArea" rows="3" readonly style="font-family: monospace; font-size: 0.74rem; background: var(--bg-surface-secondary);"></textarea>
          <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 0.35rem;">
            <span style="font-size: 0.74rem; color: var(--text-muted);" id="jwtExpirationDisplay">الرمز صالح لجلسة العمل الحالية</span>
            <button class="btn btn-outline btn-sm" style="font-size: 0.72rem; padding: 0.2rem 0.5rem;" onclick="copyJwtTokenToClipboard()">نسخ الرمز</button>
          </div>
        </div>

        <div style="display: flex; gap: 0.5rem; justify-content: flex-end; margin-top: 1.25rem;">
          <button class="btn btn-outline btn-sm" onclick="testBackendApiPing()">⚡ فحص الاستجابة (Ping)</button>
          <button class="btn btn-primary btn-sm" onclick="saveBackendApiUrl()">💾 حفظ وتحديث الرابط</button>
        </div>
      </div>
    </div>
  </div>
`;

if (!html.includes('id="backendConfigModalBackdrop"')) {
  html = html.replace('<!-- =============================================================\n       AUTHENTICATION & USER SWITCHING MODAL', `${backendModalHTML}\n\n  <!-- =============================================================\n       AUTHENTICATION & USER SWITCHING MODAL`);
  console.log('✓ Inserted backendConfigModalBackdrop HTML');
}

fs.writeFileSync('index.html', html, 'utf8');
console.log('Build step 1 finished.');
`;

fs.writeFileSync('build_update.js', buildScriptContent, 'utf8');
console.log('Created build_update.js');
