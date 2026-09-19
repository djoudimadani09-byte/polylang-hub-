import re
import sys

with open('index.html', 'r', encoding='utf-8') as f:
    content = f.read()

print("Original index.html length:", len(content))

# 1. Insert CSS before </style>
css_patch = """
    /* =============================================================
       INTERACTIVE TRANSLATION & INTERPRETATION LAB STYLES
       ============================================================= */
    .lab-nav-tabs {
      display: flex;
      gap: 0.5rem;
      border-bottom: 2px solid var(--border-main);
      padding-bottom: 0.5rem;
      margin-bottom: 1.25rem;
      overflow-x: auto;
      scrollbar-width: thin;
    }
    .lab-tab-btn {
      background: var(--bg-surface-secondary);
      border: 1px solid var(--border-main);
      border-radius: var(--radius-sm);
      padding: 0.5rem 0.85rem;
      font-size: 0.85rem;
      font-weight: 800;
      color: var(--text-muted);
      cursor: pointer;
      display: inline-flex;
      align-items: center;
      gap: 0.4rem;
      white-space: nowrap;
      transition: var(--transition-fast);
    }
    .lab-tab-btn:hover {
      border-color: var(--primary);
      color: var(--primary);
    }
    .lab-tab-btn.active {
      background: var(--primary);
      color: #ffffff;
      border-color: var(--primary);
      box-shadow: 0 4px 12px rgba(229,57,53,0.25);
    }
    .lab-tab-content {
      display: none;
      animation: fadeIn 0.25s ease-in;
    }
    .lab-tab-content.active {
      display: block;
    }

    /* Terminology Matcher Game */
    .matcher-grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 0.75rem;
      margin: 1rem 0;
    }
    @media (max-width: 768px) {
      .matcher-grid {
        grid-template-columns: repeat(2, 1fr);
      }
    }
    .matcher-card {
      background: var(--bg-surface);
      border: 2px solid var(--border-main);
      border-radius: var(--radius-md);
      padding: 1rem 0.75rem;
      text-align: center;
      cursor: pointer;
      min-height: 85px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      font-size: 0.88rem;
      font-weight: 800;
      color: var(--text-main);
      transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
      user-select: none;
      position: relative;
    }
    .matcher-card:hover {
      transform: translateY(-2px);
      border-color: var(--primary);
      box-shadow: var(--shadow-sm);
    }
    .matcher-card.selected {
      border-color: var(--primary);
      background: rgba(229,57,53,0.08);
      color: var(--primary-dark);
      transform: scale(1.03);
    }
    .matcher-card.matched {
      border-color: var(--success);
      background: rgba(16,185,129,0.12);
      color: var(--success);
      cursor: default;
      pointer-events: none;
      opacity: 0.75;
    }
    .matcher-card.matched::after {
      content: ' ✓';
      font-size: 0.8rem;
      font-weight: 900;
    }
    .matcher-card.wrong {
      border-color: var(--danger);
      background: rgba(239,68,68,0.15);
      color: var(--danger);
      animation: matcherShake 0.3s ease;
    }
    @keyframes matcherShake {
      0%, 100% { transform: translateX(0); }
      25% { transform: translateX(-6px); }
      75% { transform: translateX(6px); }
    }

    /* Rozan Shorthand Chips */
    .rozan-palette {
      display: flex;
      flex-wrap: wrap;
      gap: 0.45rem;
      margin: 0.75rem 0;
    }
    .rozan-chip {
      background: var(--bg-surface-secondary);
      border: 1px solid var(--border-main);
      border-radius: 20px;
      padding: 0.3rem 0.65rem;
      font-size: 0.82rem;
      font-weight: 800;
      color: var(--text-main);
      cursor: pointer;
      display: inline-flex;
      align-items: center;
      gap: 0.35rem;
      transition: var(--transition-fast);
    }
    .rozan-chip:hover {
      background: rgba(229,57,53,0.1);
      border-color: var(--primary);
      color: var(--primary);
      transform: scale(1.05);
    }
    .rozan-chip-symbol {
      background: var(--primary-subtle);
      color: var(--primary);
      border-radius: 4px;
      padding: 1px 5px;
      font-weight: 900;
      font-family: monospace;
    }

    /* Subtitling CPS meter */
    .cps-indicator-box {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0.75rem 1rem;
      background: var(--bg-surface-secondary);
      border-radius: var(--radius-sm);
      border: 1px solid var(--border-main);
      margin-top: 0.5rem;
    }
    .cps-pill {
      padding: 0.25rem 0.65rem;
      border-radius: 50px;
      font-size: 0.78rem;
      font-weight: 800;
    }
    .cps-pill.ok {
      background: rgba(16,185,129,0.15);
      color: var(--success);
      border: 1px solid var(--success);
    }
    .cps-pill.warn {
      background: rgba(245,158,11,0.15);
      color: var(--warning);
      border: 1px solid var(--warning);
    }
    .cps-pill.bad {
      background: rgba(239,68,68,0.15);
      color: var(--danger);
      border: 1px solid var(--danger);
    }

    /* Admin Email Notifications */
    .admin-email-badge {
      display: inline-flex;
      align-items: center;
      gap: 0.35rem;
      background: rgba(16,185,129,0.12);
      color: #065f46;
      border: 1px solid #a7f3d0;
      border-radius: 50px;
      padding: 0.25rem 0.75rem;
      font-size: 0.78rem;
      font-weight: 800;
    }
    [data-theme="dark"] .admin-email-badge {
      background: rgba(16,185,129,0.25);
      color: #6ee7b7;
      border-color: #047857;
    }
"""

if css_patch.strip() not in content:
    content = content.replace('</style>', css_patch + '\n  </style>', 1)
    print("✓ Injected CSS styles before </style>")

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(content)
print("Updated index.html CSS!")
