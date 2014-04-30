'use strict';

var e2eUtils = require('../util/util.js');
var BaseWebPage = require('./baseWebPage.js');
var util = require('util');

function ScenarioPage(overridePath) {
    if (overridePath && overridePath.length > 0) {
        BaseWebPage.call(this, overridePath);
    } else {
        BaseWebPage.call(this, '/');
    }

    this.stepView = element(by.css('div.step-view'));
}

function expandAllButton() {
    return element(by.id('expandAllPages'));
}

function collapseAllButton() {
    return element(by.id('collapseAllPages'));
}


util.inherits(ScenarioPage, BaseWebPage);

ScenarioPage.prototype.openStepByName = function (stepName) {
    this.stepView.findElement(by.linkText(stepName)).then(function (element) {
        element.click();
    });
};

ScenarioPage.prototype.toggleShowAllStepsOfPage = function (pageIndex) {
    this.stepView.findElements(by.css('.toggle-show-all-steps-of-page')).then(function(elements) {
        elements[pageIndex].click();
    });
};

ScenarioPage.prototype.expectOnlyExpandAllButtonIsDisplayed = function () {
    expect(expandAllButton().isDisplayed()).toBeTruthy();
    expect(collapseAllButton().isDisplayed()).toBeFalsy();
};

ScenarioPage.prototype.expectOnlyCollapseAllButtonIsDisplayed = function () {
    expect(expandAllButton().isDisplayed()).toBeFalsy();
    expect(collapseAllButton().isDisplayed()).toBeTruthy();
};

ScenarioPage.prototype.expectExpandAllAndCollapseAllButtonBothDisplayed = function () {
    expect(expandAllButton().isDisplayed()).toBeTruthy();
    expect(collapseAllButton().isDisplayed()).toBeTruthy();
};


module.exports = ScenarioPage;