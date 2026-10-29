# Security Policy

## Supported versions

The project currently supports the latest default branch and the active security-hardening branch.

## Reporting a vulnerability

Please report security issues privately and do not open a public issue for vulnerabilities.

Use one of the following:
- open a private security advisory in GitHub, if available for the repository
- contact the repository maintainer directly through a non-public channel
- send a report to a designated support address such as `support@yourdomain.com`

## Security requirements

- Never commit real credentials, personal email addresses, private keys, or production secrets.
- Use environment variables or a secrets manager for runtime values.
- Keep `.env`, `.env.*`, `local.properties`, keystores, and certificates out of Git.
- Do not expose admin contact data or personal identifiers in source code or public documentation.
- Review Git history before publishing a public repository to ensure leaked secrets are removed.

## Secret handling

- Use `.env.example` as a template only.
- Keep real values in local developer environments or CI secrets.
- Prefer BuildConfig or runtime injection for configuration values.

## Response expectations

Reports will be reviewed as quickly as possible. If a vulnerability is confirmed, the responsible maintainers will work to patch and disclose the issue in line with secure disclosure practices.
