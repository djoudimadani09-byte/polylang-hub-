#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import sys

def main():
    with open("index.html", "r", encoding="utf-8") as f:
        html = f.read()

    # 1. Add booth_100_speeches_data.js to head if not present
    if "booth_100_speeches_data.js" not in html:
        target = '<script src="placement_exams_100_data.js"></script>'
        replacement = '<script src="booth_100_speeches_data.js"></script>\n  <script src="placement_exams_100_data.js"></script>'
        if target in html:
            html = html.replace(target, replacement, 1)
            print("Added booth_100_speeches_data.js to head.")

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
        html = html.replace(old_video_switch, new_video_switch, 1)
        print("Updated video engine logic for smooth German video playback.")

    with open("index.html", "w", encoding="utf-8") as f:
        f.write(html)
    print("Saved basic index.html updates.")

if __name__ == "__main__":
    main()
