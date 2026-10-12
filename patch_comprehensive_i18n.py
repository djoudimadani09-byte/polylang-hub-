# -*- coding: utf-8 -*-
import json
import re

with open('full_i18n_dictionary.json', 'r', encoding='utf-8') as f:
    DICTIONARY = json.load(f)

with open('index.html', 'r', encoding='utf-8') as f:
    html = f.read()

# Generate the JS injection for recursive phrase translation and dom scanner
dict_js = json.dumps(DICTIONARY, ensure_ascii=False)

injection = f"""
    // Universal Comprehensive DOM i18n Translation Engine (All 7 Languages)
    const UNIVERSAL_I18N_DICT = {dict_js};

    function translateEntireDOM(lang) {{
      if (!lang || lang === 'ar') {{
        // Restore Arabic original text
        if (window.__originalDomTexts) {{
          window.__originalDomTexts.forEach(item => {{
            if (item.node && item.text) item.node.nodeValue = item.text;
            if (item.element && item.attr && item.val) item.element.setAttribute(item.attr, item.val);
          }});
        }}
        return;
      }}

      // Initialize backup on first run
      if (!window.__originalDomTexts) {{
        window.__originalDomTexts = [];
        const walker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null, false);
        let node;
        while ((node = walker.nextNode())) {{
          const txt = node.nodeValue.trim();
          if (txt.length > 1) {{
            window.__originalDomTexts.push({{ node: node, text: node.nodeValue }});
          }}
        }}
        // Backup inputs and placeholders
        document.querySelectorAll('input, textarea, select').forEach(el => {{
          if (el.placeholder) window.__originalDomTexts.push({{ element: el, attr: 'placeholder', val: el.placeholder }});
          if (el.title) window.__originalDomTexts.push({{ element: el, attr: 'title', val: el.title }});
        }});
      }}

      // Translate text nodes using Universal Dictionary
      window.__originalDomTexts.forEach(item => {{
        if (item.node && item.text) {{
          let currentText = item.text;
          const trimmed = currentText.trim();
          if (UNIVERSAL_I18N_DICT[trimmed] && UNIVERSAL_I18N_DICT[trimmed][lang]) {{
            item.node.nodeValue = item.text.replace(trimmed, UNIVERSAL_I18N_DICT[trimmed][lang]);
          }} else {{
            // Substring search for embedded phrases
            for (const [arKey, transMap] of Object.entries(UNIVERSAL_I18N_DICT)) {{
              if (arKey.length > 5 && currentText.includes(arKey) && transMap[lang]) {{
                currentText = currentText.replaceAll(arKey, transMap[lang]);
              }}
            }}
            item.node.nodeValue = currentText;
          }}
        }}
      }});
    }}
"""

# Let's check where changeLanguage function is and attach translateEntireDOM(lang)
if 'function changeLanguage(lang) {' in html:
    # Insert UNIVERSAL_I18N_DICT right before changeLanguage
    html = html.replace('function changeLanguage(lang) {', injection + '\n    function changeLanguage(lang) {')
    print('Inserted UNIVERSAL_I18N_DICT and translateEntireDOM')

# Call translateEntireDOM(lang); at the end of changeLanguage(lang)
end_target = "setEl('lbl-account-status', t.accountStatus);"
if end_target in html:
    html = html.replace(end_target, end_target + '\n      try { translateEntireDOM(lang); } catch (e) { console.error("i18n err:", e); }')
    print('Attached translateEntireDOM(lang) call to end of changeLanguage')
else:
    print('Warning: end_target not found in changeLanguage')

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(html)

print('Updated index.html with universal DOM translator successfully!')
