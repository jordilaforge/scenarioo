'use strict';

exports.config = {

    // The location of the selenium standalone server .jar file.
    seleniumServerJar: './node_modules/protractor/selenium/selenium-server-standalone-2.37.0.jar',
    // find its own unused port.
    seleniumPort: null,
    chromeDriver: './node_modules/protractor/selenium/chromedriver',
    seleniumArgs: [],

    allScriptsTimeout: 11000,

    specs: [
        'test/protractorE2E/specs/*_spec.js'
    ],

    capabilities: {
        'browserName': 'chrome'
    },

    // use this to run webtests against our cloud deployment
    // baseUrl: 'http://www.hatch-showcase.ch/webclient',
    baseUrl: 'http://localhost:9000',

    rootElement: 'body',

    onPrepare: function () {
    },

    params: {
        // use this to run webtests against our cloud deployment
        // baseUrl: 'http://www.hatch-showcase.ch/webclient',
        baseUrl: 'http://localhost:9000'
    },

    jasmineNodeOpts: {
        // onComplete will be called just before the driver quits.
        onComplete: null,
        // If true, display spec names.
        isVerbose: false,
        // If true, print colors to the terminal.
        showColors: true,
        // If true, include stack traces in failures.
        includeStackTrace: true,
        // Default time to wait in ms before a test fails.
        defaultTimeoutInterval: 30000
    }
};
