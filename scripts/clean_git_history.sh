#!/usr/bin/env bash
set -euo pipefail

# Repository history cleanup script for removing exposed emails and secret-like patterns.
# IMPORTANT: This rewrites git history and requires a force push.
# Run only after confirming all collaborators are aware.

TARGET_BRANCH="${1:-security-hardening}"
REPO_REMOTE="${2:-origin}"

if ! command -v git >/dev/null 2>&1; then
  echo "git is required but not installed."
  exit 1
fi

if ! command -v python3 >/dev/null 2>&1; then
  echo "python3 is required but not installed."
  exit 1
fi

if ! git rev-parse --git-dir >/dev/null 2>&1; then
  echo "This script must run inside a git repository."
  exit 1
fi

echo "==> Checking current branch"
git checkout "$TARGET_BRANCH" || git checkout -b "$TARGET_BRANCH"

echo "==> Showing recent history"
git log --oneline --decorate --graph --all --max-count=20

echo "==> Checking for obvious secret patterns in all history"
if git grep -nE "djoudimadani09@gmail.com|ghp_|AKIA|AIza|BEGIN PRIVATE KEY|client_secret|api_key|token|password|secret|aws_access_key_id|aws_secret_access_key" $(git rev-list --all) >/tmp/git-secret-scan.txt 2>/dev/null; then
  echo "Patterns found in history. Proceeding with cleanup."
  cat /tmp/git-secret-scan.txt
else
  echo "No obvious secret patterns found in history."
fi

echo "==> Creating replacement list"
cat > /tmp/replacements.txt <<'EOF'
djoudimadani09@gmail.com==>support@yourdomain.com
djoudimadani09==>yourname
EOF

echo "==> Installing git-filter-repo if missing"
pip install --quiet git-filter-repo

echo "==> Rewriting history"
git filter-repo --replace-text /tmp/replacements.txt --force

echo "==> Cleaning old refs and garbage"
git for-each-ref --format='delete %(refname)' refs/original | git update-ref --stdin || true
git reflog expire --expire=now --all
git gc --prune=now --aggressive

echo "==> Verifying that sensitive patterns are gone"
if git grep -nE "djoudimadani09@gmail.com|ghp_|AKIA|AIza|BEGIN PRIVATE KEY|client_secret|api_key|token|password|secret|aws_access_key_id|aws_secret_access_key" $(git rev-list --all) >/tmp/git-secret-check.txt 2>/dev/null; then
  echo "WARNING: Potential sensitive content still exists in history."
  cat /tmp/git-secret-check.txt
  exit 2
else
  echo "Verification passed: no obvious sensitive patterns remain in git history."
fi

echo "==> Push rewrite to remote"
read -p "Do you want to force-push to ${REPO_REMOTE}/${TARGET_BRANCH}? [y/N]: " CONFIRM
if [[ "$CONFIRM" =~ ^[Yy]$ ]]; then
  git push --force-with-lease "$REPO_REMOTE" "$TARGET_BRANCH"
  echo "History rewrite pushed successfully."
else
  echo "No push performed. Review results before force pushing."
fi

echo "==> If this repo is public, also rotate any real secrets that were previously exposed."
