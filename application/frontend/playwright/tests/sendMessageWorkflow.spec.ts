import { test, expect } from '@playwright/test';

test('has title', async ({ page }) => {
  await page.goto('http://localhost:4200/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Esbot/);
});

test('send message', async ({ page }) => {
    await page.goto('http://localhost:4200/');

    await page.waitForLoadState('networkidle');
    let listOfSessions = page.locator('[data-testid^="session-item-"]') //AI USAGE: How to count items with similar, but not same, data-testid
    let numberOfSessions = await listOfSessions.count();


    if(numberOfSessions === 0) {
        const newSessionButton = page.locator('[data-testid="new-session-btn"]')
        await newSessionButton.click();
        listOfSessions = page.locator('[data-testid^="session-item-"]');
        numberOfSessions+=1;
    }
    
    const session = listOfSessions.nth(numberOfSessions-1);
    const sessionId = await session.getAttribute('data-session-id');

    await session.click();

    const activeSession = page.locator('.active-session');
    const activeSessionId = await activeSession.getAttribute('data-session-id');

    expect(activeSessionId).toBe(sessionId);

    await expect(activeSession).toHaveCount(1);
    await expect(activeSession).toBeVisible();

    const messageInputField = page.locator('[data-testid="message-input-field"]')
    await expect(messageInputField).toBeVisible();

    await page.getByTestId('session-item-' + sessionId).click();
    await page.getByTestId('message-input-field').click();
    await expect(messageInputField).toBeFocused();

    const testMessage = 'What is End-to-End-Testing?'
    await messageInputField.fill(testMessage);
    const chatMessages = page.locator('[data-testid*="-message-"]');

    const initialMessageCount = await chatMessages.count();

    await expect(messageInputField).toHaveValue(testMessage);
    await page.getByTestId('send-message-button').filter({ hasText: 'send' }).click();
    await expect(messageInputField).toHaveValue('');

    await expect(chatMessages).toHaveCount(initialMessageCount+2);

    const userMessage = chatMessages.nth(initialMessageCount);
    const kIresponse = chatMessages.nth(initialMessageCount+1);

    await expect(userMessage).toBeVisible();
    await expect(userMessage).toContainText(testMessage);

    await expect(kIresponse).toBeVisible();
    await expect(kIresponse).not.toBeEmpty();
});