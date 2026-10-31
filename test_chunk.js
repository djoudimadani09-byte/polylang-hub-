
    function renderStudentSharedLibrary() {
      applyStudentSharedFilters();
      renderGlossaryCards();
    }

    function applyStudentSharedFilters() {
      const container = document.getElementById('student-academy-cards-container');
      const counterEl = document.getElementById('student-shared-status-counter');
      if (!container) return;

      const all = typeof getAllAcademyVideos === 'function' ? getAllAcademyVideos() : [];
      const searchInput = document.getElementById('student-shared-search');
      const q = searchInput ? searchInput.value.trim().toLowerCase() : '';
      const catEl = document.getElementById('student-shared-category-filter');
      const cat = catEl ? catEl.value : studentSharedCurrentCategory;
      const levelEl = document.getElementById('student-shared-level-filter');
      const level = levelEl ? levelEl.value : studentSharedCurrentLevel;

      const filtered = all.filter(item => {
        if (q) {
          const matchTitle = (item.title || '').toLowerCase().includes(q);
          const matchDesc = (item.desc || '').toLowerCase().includes(q);
          const matchCat = (item.categoryLabel || item.category || '').toLowerCase().includes(q);
          if (!matchTitle && !matchDesc && !matchCat) return false;
        }
        if (cat !== 'all' && item.category !== cat) return false;
        if (level !== 'all' && item.level !== level) return false;
        return true;
      });

      if (counterEl) {
        counterEl.textContent = `عرض ${filtered.length} من أصل ${all.length} مرجعاً ومساقاً أكاديمياً ولغوياً معتمداً`;
      }

      container.innerHTML = '';
      if (filtered.length === 0) {
        container.innerHTML = `
          <div style="grid-column:1/-1; text-align:center; padding:35px 20px; background:var(--bg-body); border-radius:12px; border:1px dashed var(--border-line);">
            <div style="font-size:32px; margin-bottom:8px;">🔍</div>
            <h4 style="font-size:15px; font-weight:800; margin-bottom:6px;">لم يتم العثور على مراجع مطابقة</h4>
            <p style="font-size:12px; color:var(--text-dim); margin-bottom:12px;">جرب تغيير كلمة البحث أو تصفير الفلاتر.</p>
            <button class="btn-outline-action" onclick="resetStudentSharedFilters()">إعادة ضبط الفلاتر 🔄</button>
          </div>
        `;
        return;
      }

      filtered.forEach(item => {
        const card = document.createElement('div');
        card.className = 'course-card';
        card.style.cssText = 'display:flex; flex-direction:column; justify-content:space-between; background:var(--bg-card); border:1px solid var(--border-line); border-radius:12px; padding:14px;';
        const formatIcon = item.formatIcon || '📖';
        card.innerHTML = `
          <div>
            <!-- Banner Header -->
            <div onclick="openVideoPlayer('${item.id}')" style="cursor:pointer; background:linear-gradient(135deg, #1e293b, #0f172a); border-radius:10px; padding:16px 14px; color:#fff; position:relative; overflow:hidden; margin-bottom:10px; box-shadow:0 3px 10px rgba(0,0,0,0.12);">
              <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:8px;">
                <span style="font-size:26px;">${formatIcon}</span>
                <span style="background:rgba(255,255,255,0.18); color:#fff; font-size:10px; font-weight:800; padding:2px 8px; border-radius:4px;">
                  ${item.format || 'مرجع أكاديمي'}
                </span>
              </div>
              <div style="font-size:11px; color:#38bdf8; font-weight:700; margin-bottom:3px;">
                ${item.categoryLabel || 'علم الترجمة'}
              </div>
              <div style="font-size:13.5px; font-weight:900; line-height:1.4; color:#fff;">
                ${item.title}
              </div>
            </div>

            <!-- Meta Tags -->
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:6px; flex-wrap:wrap; gap:4px;">
              <span style="font-size:10.5px; font-weight:800; color:var(--crimson);">${item.categoryLabel || item.category || 'مساق تخصصي'}</span>
              <span style="font-size:10px; color:var(--text-dim); background:var(--bg-body); padding:2px 6px; border-radius:4px;">${item.level || 'معتمد'}</span>
            </div>
            <div style="display:inline-block; font-size:10.5px; color:#059669; font-weight:800; background:#ecfdf5; padding:2px 6px; border-radius:4px; margin-bottom:8px;">
              ${item.verifiedBadge || '✓ رابط أكاديمي موثق'}
            </div>
            <p style="font-size:12px; color:var(--text-dim); line-height:1.55; margin:0 0 12px;">
              ${item.desc}
            </p>
          </div>

          <!-- Bottom Action Buttons -->
          <div style="display:flex; gap:6px; border-top:1px solid var(--border-line); padding-top:10px;">
            <button class="btn-primary-red" style="flex:1; padding:6px 10px; font-size:11px; display:inline-flex; align-items:center; justify-content:center; gap:4px;" onclick="openVideoPlayer('${item.id}')">
              <span>▶ تشغيل في المنصة</span>
            </button>
            <button class="btn-outline-action" style="padding:6px 10px; font-size:11px;" onclick="${item.url ? `openResourceLink('${item.url}')` : `openVideoPlayer('${item.id}')`}">
              <span>${item.url && item.url.includes('facebook') ? '🔗 فيسبوك ↗' : '🔗 الرابط المباشر ↗'}</span>
            </button>
          </div>
        `;
        container.appendChild(card);
      });
    }

    function handleStudentSharedFilterChange() {
      const catEl = document.getElementById('student-shared-category-filter');
      if (catEl) studentSharedCurrentCategory = catEl.value;
      const lvlEl = document.getElementById('student-shared-level-filter');
      if (lvlEl) studentSharedCurrentLevel = lvlEl.value;
      applyStudentSharedFilters();
    }

    function setStudentCategoryFilter(cat, btn) {
      studentSharedCurrentCategory = cat;
      const catEl = document.getElementById('student-shared-category-filter');
      if (catEl) catEl.value = cat;
      if (btn && btn.parentElement) {
        btn.parentElement.querySelectorAll('.nav-pill').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
      }
      applyStudentSharedFilters();
    }

    function resetStudentSharedFilters() {
      const search = document.getElementById('student-shared-search');
      if (search) search.value = '';
      const cat = document.getElementById('student-shared-category-filter');
      if (cat) cat.value = 'all';
      const lvl = document.getElementById('student-shared-level-filter');
      if (lvl) lvl.value = 'all';
      studentSharedCurrentCategory = 'all';
      studentSharedCurrentLevel = 'all';
      document.querySelectorAll('#student-cat-chips-container .nav-pill').forEach((b, i) => b.classList.toggle('active', i === 0));
      applyStudentSharedFilters();
    }

    function switchSharedLibrarySection(sec) {
      const sCourses = document.getElementById('student-shared-courses-section');
      const sTheories = document.getElementById('student-shared-theories-section');
      const sGerman = document.getElementById('student-shared-german-section');
      const sGlossary = document.getElementById('student-shared-glossary-section');
      const sStandards = document.getElementById('student-shared-standards-section');
      const sFilters = document.getElementById('student-shared-filters-toolbar');

      const tabs = ['all', 'theories', 'german', 'glossary', 'standards'];
      tabs.forEach(t => {
        const btn = document.getElementById('pill-shared-tab-' + t);
        if (btn) btn.classList.toggle('active', t === sec);
      });

      if (sec === 'all') {
        if (sCourses) sCourses.style.display = 'block';
        if (sTheories) sTheories.style.display = 'block';
        if (sGerman) sGerman.style.display = 'block';
        if (sGlossary) sGlossary.style.display = 'block';
        if (sStandards) sStandards.style.display = 'block';
        if (sFilters) sFilters.style.display = 'block';
      } else if (sec === 'theories') {
        if (sCourses) sCourses.style.display = 'none';
        if (sTheories) sTheories.style.display = 'block';
        if (sGerman) sGerman.style.display = 'none';
        if (sGlossary) sGlossary.style.display = 'none';
        if (sStandards) sStandards.style.display = 'none';
        if (sFilters) sFilters.style.display = 'none';
      } else if (sec === 'german') {
        if (sCourses) sCourses.style.display = 'block';
        if (sTheories) sTheories.style.display = 'none';
        if (sGerman) sGerman.style.display = 'block';
        if (sGlossary) sGlossary.style.display = 'none';
        if (sStandards) sStandards.style.display = 'none';
        if (sFilters) sFilters.style.display = 'block';
        setStudentCategoryFilter('german_learning', null);
      } else if (sec === 'glossary') {
        if (sCourses) sCourses.style.display = 'none';
        if (sTheories) sTheories.style.display = 'none';
        if (sGerman) sGerman.style.display = 'none';
        if (sGlossary) sGlossary.style.display = 'block';
        if (sStandards) sStandards.style.display = 'none';
        if (sFilters) sFilters.style.display = 'none';
      } else if (sec === 'standards') {
        if (sCourses) sCourses.style.display = 'none';
        if (sTheories) sTheories.style.display = 'none';
        if (sGerman) sGerman.style.display = 'none';
        if (sGlossary) sGlossary.style.display = 'none';
        if (sStandards) sStandards.style.display = 'block';
        if (sFilters) sFilters.style.display = 'none';
      }
    }

    function renderGlossaryCards() {
      const container = document.getElementById('glossary-cards-container');
      if (!container) return;

      const searchInput = document.getElementById('glossary-term-search');
      const q = searchInput ? searchInput.value.trim().toLowerCase() : '';

      const items = TRILINGUAL_GLOSSARY_ITEMS.filter(it => {
        if (currentGlossaryDomain !== 'all' && it.domain !== currentGlossaryDomain) return false;
        if (q) {
          const mAr = it.ar.toLowerCase().includes(q);
          const mEn = it.en.toLowerCase().includes(q);
          const mDe = it.de.toLowerCase().includes(q);
          const mFr = it.fr.toLowerCase().includes(q);
          const mCtx = it.context.toLowerCase().includes(q);
          if (!mAr && !mEn && !mDe && !mFr && !mCtx) return false;
        }
        return true;
      });

      container.innerHTML = '';
      if (items.length === 0) {
        container.innerHTML = '<div style="grid-column:1/-1; text-align:center; padding:20px; font-size:12px; color:var(--text-dim);">لم يتم العثور على مصطلحات مطابقة للبحث.</div>';
        return;
      }

      items.forEach(it => {
        const card = document.createElement('div');
        card.style.cssText = 'background:var(--bg-body); border:1px solid var(--border-line); border-radius:10px; padding:12px; display:flex; flex-direction:column; justify-content:space-between; gap:6px;';
        card.innerHTML = `
          <div>
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:4px;">
              <span style="font-size:10px; background:var(--bg-surface); padding:2px 6px; border-radius:4px; font-weight:700; color:var(--crimson);">${it.domainLabel}</span>
              <button class="nav-pill" style="padding:2px 6px; font-size:10px;" onclick="copyGlossaryTerm('${escapeHtml(it.en)} | ${escapeHtml(it.ar)}', this)">📋 نسخ</button>
            </div>
            <div style="font-size:13.5px; font-weight:900; color:var(--text-main); margin-bottom:4px;">${it.ar}</div>
            
            <div style="font-size:11.5px; color:#1d4ed8; font-weight:700; display:flex; align-items:center; justify-content:space-between;">
              <span>🇬🇧 ${it.en}</span>
              <button onclick="playGlossaryTermAudio('${escapeHtml(it.en)}', 'en-US')" style="background:none; border:none; cursor:pointer; font-size:13px;" title="استماع للنطق">🔊</button>
            </div>
            
            <div style="font-size:11.5px; color:#b45309; font-weight:700; display:flex; align-items:center; justify-content:space-between;">
              <span>🇩🇪 ${it.de}</span>
              <button onclick="playGlossaryTermAudio('${escapeHtml(it.de)}', 'de-DE')" style="background:none; border:none; cursor:pointer; font-size:13px;" title="استماع للنطق بالألمانية">🔊</button>
            </div>

            <div style="font-size:11px; color:#475569; font-weight:600; display:flex; align-items:center; justify-content:space-between;">
              <span>🇫🇷 ${it.fr}</span>
              <button onclick="playGlossaryTermAudio('${escapeHtml(it.fr)}', 'fr-FR')" style="background:none; border:none; cursor:pointer; font-size:13px;" title="استماع للنطق بالفرنسية">🔊</button>
            </div>
          </div>
          <div style="font-size:10.5px; color:var(--text-dim); background:var(--bg-card); padding:5px 8px; border-radius:6px; line-height:1.45; border-top:1px dashed var(--border-line);">
            ${it.context}
          </div>
        `;
        container.appendChild(card);
      });
    }

    function filterGlossaryDomain(domain, btn) {
      currentGlossaryDomain = domain;
      if (btn && btn.parentElement) {
        btn.parentElement.querySelectorAll('.nav-pill').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
      }
      renderGlossaryCards();
    }

    function handleGlossarySearch() {
      renderGlossaryCards();
    }

    function playGlossaryTermAudio(text, lang) {
      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
        const ut = new SpeechSynthesisUtterance(text);
        ut.lang = lang || 'en-US';
        ut.rate = 0.9;
        window.speechSynthesis.speak(ut);
      } else {
        alert('ميزة النطق الصوتي تتطلب متصفحاً يدعم Web Speech Synthesis.');
      }
    }

    function copyGlossaryTerm(term, btn) {
      navigator.clipboard.writeText(term).then(() => {
        const orig = btn.textContent;
        btn.textContent = '✓ تم النسخ';
        btn.style.color = '#10b981';
        setTimeout(() => {
          btn.textContent = orig;
          btn.style.color = '';
        }, 1500);
      }).catch(() => {
        alert('تم النسخ: ' + term);
      });
    }

window.addEventListener('DOMContentLoaded', () => {
      restoreUserSession();
      applyBoothFilters();
      recalcQuote();
      renderOrdersTable();
      renderAdminDashboard();
      renderVisitorReviewsHome();
      applyAcademyFilters();
      initSrtStudio();
      try {
        initMatchGame();
        renderFalseFriendsGame();
        renderSentenceBuilderGame();
        renderBoothReflexGame();
      } catch (err) {
        console.log("Game init note:", err);
}}