# -*- coding: utf-8 -*-
import json
import re

with open('index.html', 'r', encoding='utf-8') as f:
    html = f.read()

print("Read index.html successfully, length:", len(html))
