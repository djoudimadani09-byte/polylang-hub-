# -*- coding: utf-8 -*-
import json

with open('booth_100_speeches_data.js', 'r', encoding='utf-8') as f:
    js_text = f.read()

# Extract json
raw = js_text.split('var BOOTH_100_SPEECHES = ')[1].split(';\n\nif (typeof window')[0]
speeches = json.loads(raw)

kt_code = '''package com.example

/**
 * 120 Standardized Conference Interpretation & Consecutive Speeches Bank
 * ISO 17100:2015 Accredited Simulation.
 */
data class ConferenceSpeech(
    val id: String,
    val num: Int,
    val domainId: String,
    val domainAr: String,
    val domainEn: String,
    val icon: String,
    val titleAr: String,
    val titleEn: String,
    val speaker: String,
    val mode: String, // simultaneous, consecutive, drill
    val isPaid: Boolean,
    val langPair: String,
    val audioLang: String,
    val sourceText: String,
    val modelSolution: String,
    val terms: List<String>,
    val rozanSummary: String
)

object BoothSpeechesBank {
    val allSpeeches: List<ConferenceSpeech> = listOf(
'''

items = []
for sp in speeches:
    # Escape quotes
    source_esc = sp['source'].replace('\\', '\\\\').replace('"', '\\"').replace('$', '\\$')
    sol_esc = sp['solution'].replace('\\', '\\\\').replace('"', '\\"').replace('$', '\\$')
    title_ar_esc = sp['title_ar'].replace('"', '\\"')
    title_en_esc = sp['title_en'].replace('"', '\\"')
    speaker_esc = sp['speaker'].replace('"', '\\"')
    rozan_esc = sp['rozan'].replace('\\', '\\\\').replace('"', '\\"').replace('$', '\\$')
    terms_list = ', '.join([f'"{t.replace(chr(34), chr(92)+chr(34))}"' for t in sp.get('terms', [])])
    
    item = f'''        ConferenceSpeech(
            id = "{sp['id']}",
            num = {sp['num']},
            domainId = "{sp['domain_id']}",
            domainAr = "{sp['domain_ar']}",
            domainEn = "{sp['domain_en']}",
            icon = "{sp['icon']}",
            titleAr = "{title_ar_esc}",
            titleEn = "{title_en_esc}",
            speaker = "{speaker_esc}",
            mode = "{sp['mode']}",
            isPaid = {str(sp['tier'] == 'paid').lower()},
            langPair = "{sp['pair']}",
            audioLang = "{sp['audio_lang']}",
            sourceText = "{source_esc}",
            modelSolution = "{sol_esc}",
            terms = listOf({terms_list}),
            rozanSummary = "{rozan_esc}"
        )'''
    items.append(item)

kt_code += ',\n'.join(items) + '\n    )\n}\n'

with open('app/src/main/java/com/example/BoothSpeechesBank.kt', 'w', encoding='utf-8') as out_f:
    out_f.write(kt_code)

print(f"Updated BoothSpeechesBank.kt with {len(speeches)} speeches successfully!")
