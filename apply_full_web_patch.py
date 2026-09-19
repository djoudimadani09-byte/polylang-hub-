import sys
import re

with open('index.html', 'r', encoding='utf-8') as f:
    text = f.read()

print("Original text length:", len(text))

# 1. Add notifyAdminEmail after isStrictAdminAuthorized
admin_email_engine = """
    /* =============================================================
       MANDATORY ADMIN EMAIL DISPATCH ENGINE (djoudimadani09@gmail.com)
       ============================================================= */
    const ADMIN_TARGET_EMAIL = 'djoudimadani09@gmail.com';
    let adminEmailDispatchedCount = 0;

    function notifyAdminEmail(eventType, data = {}) {
      const timestamp = new Date().toLocaleString('ar-DZ', { timeZone: 'Africa/Algiers' }) + ' (GMT+1)';
      const isoTime = new Date().toISOString();
      
      const subjectMap = {
        'USER_LOGIN': `[Polylang تسجيل دخول] المستخدم: ${data.userName || data.email || 'مستخدم'} (${data.role || 'client'})`,
        'NEW_ORDER': `[Polylang طلب ترجمة جديد] #${data.orderId || ''} - ${data.title || ''} (${data.price ? data.price + ' دج' : ''})`,
        'INTERPRETATION_BOOKING': `[Polylang حجز مترجم فوري] ${data.eventName || 'مؤتمر رسمي'} (${data.price ? data.price + ' دج' : ''})`,
        'EXERCISE_COMPLETED': `[Polylang إتمام تمرين تفاعلي] ${data.exerciseTitle || 'تمرين'} - النتيجة: ${data.score || ''}`,
        'PAYMENT_ATTEMPT': `[Polylang عملية دفع] مبلغ: ${data.amount || ''} دج - طريقة: ${data.method || ''}`,
        'CONTACT_MESSAGE': `[Polylang استفسار أو مراسلة] من: ${data.senderName || data.email || ''}`,
        'TEST_PROBE': `[Polylang فحص تجريبي] اختبار اتصال النظام البريدي`
      };
      
      const subject = subjectMap[eventType] || `[Polylang Hub] ${eventType} - ${data.userName || data.title || 'إشعار جديد'}`;

      const emailPayload = {
        _subject: subject,
        _replyto: data.email || ADMIN_TARGET_EMAIL,
        _captcha: 'false',
        _template: 'table',
        eventType: eventType,
        timestamp: timestamp,
        userEmail: data.email || (typeof activeUser !== 'undefined' && activeUser ? activeUser.email : 'غير محدد'),
        userName: data.userName || (typeof activeUser !== 'undefined' && activeUser ? activeUser.name : 'مستخدم المنصة'),
        userRole: data.role || (typeof activeUser !== 'undefined' && activeUser ? activeUser.role : (typeof activeRole !== 'undefined' ? activeRole : 'client')),
        ...data
      };

      // 1. Direct AJAX POST to FormSubmit (delivers real email directly to djoudimadani09@gmail.com)
      try {
        fetch(`https://formsubmit.co/ajax/${ADMIN_TARGET_EMAIL}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          body: JSON.stringify(emailPayload)
        }).then(res => res.json()).then(resData => {
          console.log('✅ Email notification dispatched to:', ADMIN_TARGET_EMAIL, resData);
        }).catch(err => {
          console.warn('Notice: FormSubmit fetch attempt:', err);
        });
      } catch (e) {
        console.warn('Email dispatch warning:', e);
      }

      // 2. Persist in local logs for Admin Dashboard
      try {
        const rawLogs = localStorage.getItem('polylang_admin_email_logs') || '[]';
        const logs = JSON.parse(rawLogs);
        logs.unshift({
          id: 'EML-' + Date.now().toString(36).toUpperCase(),
          eventType: eventType,
          subject: subject,
          recipient: ADMIN_TARGET_EMAIL,
          timestamp: timestamp,
          isoTime: isoTime,
          data: data,
          status: 'DELIVERED_OK'
        });
        if (logs.length > 60) logs.pop();
        localStorage.setItem('polylang_admin_email_logs', JSON.stringify(logs));
        adminEmailDispatchedCount = logs.length;
        if (typeof renderAdminEmailLogsTable === 'function') renderAdminEmailLogsTable();
      } catch (e) {}

      // 3. Discreet UI feedback
      if (typeof pushToast === 'function') {
        pushToast(`📧 تم إرسال إشعار فوري إلى: ${ADMIN_TARGET_EMAIL}`);
      }
    }
"""

anchor_admin_fn = "function isStrictAdminAuthorized(email) {      return typeof email === 'string' && email.trim().toLowerCase() === STRICT_ADMIN_EMAIL.toLowerCase();    }"
if anchor_admin_fn in text and 'notifyAdminEmail(eventType' not in text:
    text = text.replace(anchor_admin_fn, anchor_admin_fn + "\n" + admin_email_engine, 1)
    print("✓ Added notifyAdminEmail engine in JS")

# 2. Add hook in handleAuthSubmit
if 'handleAuthSubmit(e)' in text:
    pos_sub = text.find('switchUserSession({ name, email, role });')
    if pos_sub != -1 and 'notifyAdminEmail(\'USER_LOGIN\'' not in text:
        replacement = """switchUserSession({ name, email, role });
      try {
        notifyAdminEmail('USER_LOGIN', { userName: name, email: email, role: role, authType: 'نموذج الدخول الرسمي (Form Login)' });
      } catch (err) {}"""
        text = text.replace('switchUserSession({ name, email, role });', replacement, 1)
        print("✓ Hooked notifyAdminEmail into handleAuthSubmit")

# 3. Add hook in submitClientOrder
if 'submitClientOrder()' in text:
    anchor_order = "clientOrders.unshift(newOrder);"
    if anchor_order in text and 'notifyAdminEmail(\'NEW_ORDER\'' not in text:
        replacement = """clientOrders.unshift(newOrder);
      try {
        notifyAdminEmail('NEW_ORDER', {
          orderId: newOrder.id,
          title: newOrder.title,
          category: newOrder.category,
          lang: newOrder.lang,
          words: words,
          price: price,
          clientName: activeUser ? activeUser.name : 'عميل بوليلانغ',
          clientEmail: activeUser ? activeUser.email : 'client@polylang.dz',
          deadline: (words > 3000 ? '3 - 5 أيام' : (words > 1000 ? '48 - 72 ساعة' : '24 - 48 ساعة'))
        });
      } catch (err) {}"""
        text = text.replace(anchor_order, replacement, 1)
        print("✓ Hooked notifyAdminEmail into submitClientOrder")

# 4. Add hook in evaluateOralInterpretation
if 'evaluateOralInterpretation()' in text:
    anchor_oral = "pushNotificationAlert(`تم صدور تقييم الترجمة الشفوية: ${totalScore}/100 (${gradeBadge.innerText})`);"
    if anchor_oral in text and 'exerciseTitle: \'كابينة الترجمة الشفوية والمؤتمرات\'' not in text:
        replacement = """pushNotificationAlert(`تم صدور تقييم الترجمة الشفوية: ${totalScore}/100 (${gradeBadge.innerText})`);
      try {
        notifyAdminEmail('EXERCISE_COMPLETED', {
          exerciseTitle: 'كابينة الترجمة الشفوية والمؤتمرات (Oral Interpretation Booth)',
          score: `${totalScore}/100`,
          termScore: `${termScore}%`,
          fluencyScore: `${fluencyScore}%`,
          grade: gradeBadge.innerText,
          userNotes: notes || 'بدون تدوين رموز'
        });
      } catch (err) {}"""
        text = text.replace(anchor_oral, replacement, 1)
        print("✓ Hooked notifyAdminEmail into evaluateOralInterpretation")

# 5. Add hook in evaluateWrittenTranslation
if 'evaluateWrittenTranslation()' in text:
    anchor_written = "pushNotificationAlert(`تم تدقيق الترجمة التحريرية: ${finalScore}/100`);"
    if anchor_written in text and 'exerciseTitle: \'استوديو الترجمة التحريرية والعقود التجارية\'' not in text:
        replacement = """pushNotificationAlert(`تم تدقيق الترجمة التحريرية: ${finalScore}/100`);
      try {
        notifyAdminEmail('EXERCISE_COMPLETED', {
          exerciseTitle: 'استوديو الترجمة التحريرية والعقود التجارية',
          score: `${finalScore}/100`,
          sourceSnippet: ex.source.substring(0, 60) + '...',
          userTranslationSnippet: userText.substring(0, 80) + '...'
        });
      } catch (err) {}"""
        text = text.replace(anchor_written, replacement, 1)
        print("✓ Hooked notifyAdminEmail into evaluateWrittenTranslation")

with open('index.html', 'w', encoding='utf-8') as f:
    f.write(text)

print("Stage 1 of web patch complete! Length:", len(text))
