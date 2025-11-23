# UI Scaling System - Manual Test Plan

## Overview
This test plan verifies the UI scaling system works correctly across different resolutions, ensuring the game is playable at high-DPI displays (2560x1440, 4K).

## Prerequisites
- Build the project: `mvn package -DskipTests`
- Have access to displays or virtual machines at different resolutions

## Test Resolutions
| Resolution | Scale Factor | Expected Behavior |
|------------|--------------|-------------------|
| 1024x768   | 1.0x         | Baseline - all UI should match original |
| 1920x1080  | 1.41x        | UI slightly larger, all readable |
| 2560x1440  | 1.88x        | UI nearly double, fully playable |
| 3840x2160  | 2.81x        | UI scaled appropriately for 4K |

## Test Cases

### TC-001: Resolution Selection
**Steps:**
1. Launch the game at default resolution
2. Navigate to Options menu
3. Verify resolution dropdown shows: 1024x768, 1280x768, 1280x960, 1280x1024, 1440x960, 1680x1050, 1920x1080, **2560x1440**, **3840x2160**, Custom

**Expected:** New high-DPI resolutions (2560x1440, 3840x2160) appear in list

### TC-002: Main Menu Scaling
**Steps:**
1. Launch game at 2560x1440
2. Observe the main menu

**Expected:**
- [ ] Title/logo properly positioned
- [ ] Buttons readable and clickable
- [ ] Background image fills screen
- [ ] No text cutoff

### TC-003: Star Map View
**Steps:**
1. Start a new game or load existing save
2. View the star map at 2560x1440

**Expected:**
- [ ] Star map renders correctly
- [ ] Bottom info panel readable
- [ ] Side panel (MapInfoPanel) displays properly
- [ ] Buttons (Research, Ships, Stats, Leaders, End Turn) clickable
- [ ] Resource icons/labels readable

### TC-004: Font Scaling
**Steps:**
1. At 2560x1440, check text in various locations:
   - Main menu buttons
   - Info panels
   - Planet names
   - Fleet information

**Expected:**
- [ ] All text proportionally larger
- [ ] Text fits within bounds (no overflow)
- [ ] Text remains crisp/readable

### TC-005: Button Interactions
**Steps:**
1. At 2560x1440, click various SpaceButtons throughout the UI
2. Test hover states

**Expected:**
- [ ] Buttons respond to clicks
- [ ] Hover highlighting works
- [ ] Button text centered within button bounds

### TC-006: Resolution Change During Gameplay
**Steps:**
1. Start game at 1920x1080
2. Open Options, change to 2560x1440
3. Apply changes
4. Continue playing

**Expected:**
- [ ] Game resizes without crash
- [ ] UI elements scale appropriately
- [ ] Font cache invalidates (fonts refresh)
- [ ] No visual artifacts

### TC-007: Planet View
**Steps:**
1. At 2560x1440, click on a planet
2. View planet details

**Expected:**
- [ ] Planet information readable
- [ ] Building icons/text scaled
- [ ] Worker assignments visible

### TC-008: Battle View
**Steps:**
1. At 2560x1440, initiate space combat
2. Observe battle screen

**Expected:**
- [ ] Battle map renders correctly
- [ ] Ship icons visible
- [ ] Combat UI elements scaled

### TC-009: Comparison Test (Regression)
**Steps:**
1. Run game at 1024x768
2. Take screenshots of key screens
3. Run game at original code (before changes)
4. Compare screenshots

**Expected:**
- [ ] At 768p, UI should be identical to original
- [ ] No regression in base resolution support

### TC-010: Extended Play Test
**Steps:**
1. Play full game session at 2560x1440 (30+ minutes)
2. Navigate all menus
3. Complete game actions (build, research, combat)

**Expected:**
- [ ] Game fully playable
- [ ] No crashes related to UI
- [ ] All features accessible

## Known Limitations
- Map tiles remain at fixed sizes (use zoom feature for larger tiles)
- Some custom icons may appear smaller at very high resolutions

## Reporting Issues
If any test fails, document:
1. Resolution used
2. Specific UI element affected
3. Screenshot if possible
4. Steps to reproduce

## Automated Test Coverage
The following are covered by unit tests:
- UIScale.scale() calculations
- Scale factor clamping (min/max)
- Resolution tier detection
- Dimension scaling
- Font size scaling

Run tests with: `mvn test -Dtest=UIScaleTest`
