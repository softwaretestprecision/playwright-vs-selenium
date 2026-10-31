import { test, expect } from '@playwright/test';

// The same two tests are written in Selenium Java in ../selenium-java.
// Practice site: https://practicetestautomation.com/practice-test-login/

test.describe('Login', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/practice-test-login/');
  });

  test('valid credentials log the user in', async ({ page }) => {
    await page.locator('#username').fill('student');
    await page.locator('#password').fill('Password123');
    await page.locator('#submit').click();

    // Auto-waiting assertions: no explicit waits needed.
    await expect(page).toHaveURL(/logged-in-successfully/);
    await expect(page.locator('h1')).toHaveText('Logged In Successfully');
    await expect(page.getByRole('link', { name: 'Log out' })).toBeVisible();
  });

  test('invalid username shows an error', async ({ page }) => {
    await page.locator('#username').fill('incorrectUser');
    await page.locator('#password').fill('Password123');
    await page.locator('#submit').click();

    await expect(page.locator('#error')).toBeVisible();
    await expect(page.locator('#error')).toHaveText('Your username is invalid!');
  });
});
