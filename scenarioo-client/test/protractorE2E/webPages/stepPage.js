'use strict';

var e2eUtils = require('../util/util.js'), BaseWebPage = require('./baseWebPage.js'), util = require('util');

function StepPage(overridePath) {
    if (overridePath && overridePath.length > 0) {
        BaseWebPage.call(this, overridePath);
    } else {
        BaseWebPage.call(this, '/');
    }

    this.stepNavigation = element(by.css('div.step-navigation'));
}

util.inherits(StepPage, BaseWebPage);

// DISABLED
StepPage.prototype.assertPreviousStepIsDisabled = function () {
    this.assertElementIsDisabled('prevStepBtn');
};

StepPage.prototype.assertPreviousPageIsDisabled = function () {
    this.assertElementIsDisabled('prevPageBtn');
};

StepPage.prototype.assertNextStepIsDisabled = function () {
    this.assertElementIsDisabled('nextStepBtn');
};

StepPage.prototype.assertNextPageIsDisabled = function () {
    this.assertElementIsDisabled('nextPageBtn');
};

// ENABLED
StepPage.prototype.assertPreviousStepIsEnabled = function () {
    this.assertElementIsEnabled('prevStepBtn');
};

StepPage.prototype.assertPreviousPageIsEnabled = function () {
    this.assertElementIsEnabled('prevPageBtn');
};

StepPage.prototype.assertNextStepIsEnabled = function () {
    this.assertElementIsEnabled('nextStepBtn');
};

StepPage.prototype.assertNextPageIsEnabled = function () {
    this.assertElementIsEnabled('nextPageBtn');
};

StepPage.prototype.goToNextStep = function () {
    this.clickElementById('nextStepBtn');
};

StepPage.prototype.goToNextPage = function () {
    this.clickElementById('nextPageBtn');
};

StepPage.prototype.goToPreviousStep = function () {
    this.clickElementById('prevStepBtn');
};

StepPage.prototype.goToPreviousPage = function () {
    this.clickElementById('prevPageBtn');
};

StepPage.prototype.goToPreviousPageVariant = function () {
    this.clickElementById('prevPageVariantBtn');
};

StepPage.prototype.goToNextPageVariant = function () {
    this.clickElementById('nextPageVariantBtn');
};

StepPage.prototype.assertNextPageVariantButtonIsDisabled = function () {
    this.assertElementIsDisabled('nextPageVariantBtn');
};

StepPage.prototype.clickAllPageVariantsLink = function () {
    this.clickElementById('allPageVariants');
};


StepPage.prototype.assertErrorMessageIsShown = function () {
    expect(element(by.id('stepNotFoundErrorMessage')).isDisplayed()).toBeTruthy();
    expect(element(by.id('fallbackMessage')).isDisplayed()).toBeFalsy();
};

StepPage.prototype.assertFallbackMessageIsShown = function () {
    expect(element(by.id('fallbackMessage')).isDisplayed()).toBeTruthy();
    expect(element(by.id('stepNotFoundErrorMessage')).isDisplayed()).toBeFalsy();
};

StepPage.prototype.assertFallbackMessageContainsText = function(text) {
    expect(element(by.id('fallbackMessage')).getText()).toContain(text);
};

StepPage.prototype.assertScenarioLabelsContain = function(label) {
    expect(element(by.id('scenario-labels')).getInnerHtml()).toContain(label);
};

StepPage.prototype.clickShareThisPageLink = function() {
    element(by.id('shareThisPageLink')).click();
};

StepPage.prototype.assertStepLinksDialogVisible = function() {
    expect(element(by.id('stepLinksDialog')).isDisplayed()).toBeTruthy();
};

StepPage.prototype.assertPageVariantIndicatorValue = function(value) {
    var pageVariantIndicator = element(by.id('pageVariantIndicator'));
    expect(pageVariantIndicator.isDisplayed()).toBeTruthy();
    expect(pageVariantIndicator.getText()).toBe(value);
};

StepPage.prototype.openMetadataTabIfClosed = function(index) {
    var metadataPanelContentCss = '#metadata_panel_' + index + ' .metadata';

    browser.findElement(by.css(metadataPanelContentCss)).isDisplayed().then(function(displayed){
        if(!displayed) {
            element(by.id('collapsable_panel_' + index)).click();
        }
    });
};

StepPage.prototype.clickOnLink= function(linkId) {
    var ptor = protractor.getInstance();
    ptor.findElement(by.id(linkId)).then(function (element) {
        ptor.actions().click(element).perform();
    });
};

StepPage.prototype.assertToolTipInBreadcrumb = function(toolTip) {
    var ptor = protractor.getInstance();
    var toolTip = ptor.findElement(by.id('tooltip_1')).getAttribute('tooltip');
    expect(toolTip).toBe(toolTip);
};

module.exports = StepPage;
