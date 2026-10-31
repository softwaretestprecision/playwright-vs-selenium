# Video 2 — Playwright vs Selenium in 2026: Same Test in Both

The exact same two login tests, written twice, against https://practicetestautomation.com/practice-test-login/

```
code/
├── playwright/      TypeScript + @playwright/test
└── selenium-java/   Java 17 + Selenium 4 + TestNG + Maven
```

## Run the Playwright version
```bash
cd playwright
npm install
npx playwright install --with-deps chromium
npx playwright test
```

## Run the Selenium version
Requires Java 17+, Maven 3.9+, and Google Chrome installed. Selenium Manager fetches ChromeDriver for you.
```bash
cd selenium-java
mvn test                    # headless
mvn test -Dheadless=false   # watch the browser
```

## Side-by-side cheat sheet
| Task | Playwright (TS) | Selenium (Java) |
|---|---|---|
| Open page | `page.goto(url)` | `driver.get(url)` |
| Find by id | `page.locator('#username')` | `driver.findElement(By.id("username"))` |
| Type | `.fill('student')` | `.sendKeys("student")` |
| Wait + assert | `await expect(loc).toHaveText(...)` (auto-retries) | `wait.until(ExpectedConditions...)` then `Assert` |
| Browser setup | config file, browsers bundled | `ChromeOptions`, Selenium Manager finds driver |
| Cleanup | automatic per test | `driver.quit()` in `@AfterMethod` |

Starter frameworks for both tools: https://swtestprecision.gumroad.com

---

## Software Test Precision
- 📺 YouTube: https://www.youtube.com/@softwaretestprecision
- 🧰 Templates & starter frameworks: https://swtestprecision.gumroad.com

License: MIT (see `LICENSE`).
