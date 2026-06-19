// AI USAGE:
// - For Debugging & Bug Fixing 
// - Explain how to write playwright tests (generated examples)
// - As specified below (marked: 'AI USAGE')
// Cross referenced with https://playwright.dev/docs/writing-tests
import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => {
  await page.goto('http://localhost:4200/');
  await page.evaluate(() => (window as any).initUID());
});

test('has title', async ({ page }) => {
  await page.goto('http://localhost:4200/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle(/Esbot/);
});

test('createSession', async ({ page }) => {
  await page.goto('http://localhost:4200/');

  /* Flaky Test: 
  Sessions are loaded asynchronously on page init.
  The test may count the number of sessions before the initial api call + rendering finishes,
  so the baseline may not reflect the final rendered state.

  When a new session is created, additional old sessions may still be retrieved
  from the initial load, making the "+1 session" assertion unreliable.
  */

  // AI USAGE: Fix for flaky test: 
  await page.waitForLoadState('networkidle'); // wait for sessions to actually load

  // Count the number of retrived sessions
  const listOfSessions = page.locator('[data-testid^="session-item-"]') //AI USAGE: How to count items with similar, but not same, data-testid
  const numberOfSessions = await listOfSessions.count();

  // Click the "+ New Session"-Button (= new-session-btn)
  const newSessionButton = page.locator('[data-testid="new-session-btn"]')
  await newSessionButton.click();

  // Expect list of Sessions to be 1 longe than previous
  await expect(listOfSessions).toHaveCount(numberOfSessions+1);

  // Check that created Session is automatically set as active Session
  const newSession = listOfSessions.nth(numberOfSessions); // AI USAGE: How to get New Session (--> was added to end of listOfSessions)
  const activeSession = page.locator('.active-session'); //Active Session

  const newSessionId = await newSession.getAttribute('data-session-id'); // ID of New Session
  const activeSessionId = await activeSession.getAttribute('data-session-id'); // ID of Active Session

  expect(activeSessionId).toBe(newSessionId);

  // Check that only 1 session is active and is visible
  await expect(activeSession).toHaveCount(1);
  await expect(activeSession).toBeVisible();

  // Click on active session to open it 
  await activeSession.click();

  // Check that Message input field appears
  const messageInputField = page.locator('[data-testid="message-input-field"]')
  await expect(messageInputField).toBeVisible();
  });

test('createSessionNoUser', async ({ page }) => {
    await page.goto('http://localhost:4200/');
    await page.evaluate(() => {
      (window as any).changeUID();
    });

    const dialogPromise = page.waitForEvent('dialog');

    await page.locator('[data-testid="new-session-btn"]').click();
    const dialog = await dialogPromise;
    expect(dialog.message()).toBe('You are not logged in');
    await dialog.accept();
});