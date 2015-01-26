/* scenarioo-client
 * Copyright (C) 2014, scenarioo.org Development Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

'use strict';

angular.module('scenarioo.controllers').controller('StepCompareCtrl', function ($scope, $location, $routeParams, BranchesAndBuilds, BranchesAndBuildsForComparison, SelectedBranchAndBuild, StepResource, GlobalHotkeysService, HostnameAndPort) {

    var useCaseName = $routeParams.useCaseName;
    var scenarioName = $routeParams.scenarioName;
    $scope.pageName = $routeParams.pageName;
    $scope.pageOccurrence = parseInt($routeParams.pageOccurrence, 10);
    $scope.stepInPageOccurrence = parseInt($routeParams.stepInPageOccurrence, 10);
    var labels = $location.search().labels;

    $scope.modalScreenshotOptions = {
        backdropFade: true,
        dialogClass: 'modal modal-huge'
    };

    loadBranchesAndBuilds();

    function loadBranchesAndBuilds() {
        BranchesAndBuilds.getBranchesAndBuilds().then(function onSuccess(branchesAndBuilds) {
            $scope.branchesAndBuilds = branchesAndBuilds;
        }, function onFailure(error) {
            console.log(error);
        }
        );
        BranchesAndBuildsForComparison.getBranchesAndBuildsForComparison().then(function onSuccess(branchesAndBuildsForComparison) {
            $scope.branchesAndBuildsForComparison = branchesAndBuildsForComparison;
        }, function onFailure(error) {
            console.log(error);
        }
        );
    }

    SelectedBranchAndBuild.callOnSelectionChange(loadStep);

    function loadStep(selected) {
        bindStepNavigation();
        loadStepFromServer(selected);
    }

    function loadStepFromServer(selected) {
        StepResource.get(
            {
                'branchName': selected.branch,
                'buildName': selected.build,
                'branchForComparisonName': selected.compareBranch,
                'buildForComparisonName': selected.compareBuild,
                'usecaseName': useCaseName,
                'scenarioName': scenarioName,
                'pageName': $scope.pageName,
                'pageOccurrence': $scope.pageOccurrence,
                'stepInPageOccurrence': $scope.stepInPageOccurrence,
                'labels': labels
            },
            function success(result) {

                $scope.stepIdentifier = result.stepIdentifier;
                $scope.fallback = result.fallback;
                $scope.step = result.step;
                $scope.stepNavigation = result.stepNavigation;
                $scope.stepStatistics = result.stepStatistics;
                $scope.stepIndex = result.stepNavigation.stepIndex;
                $scope.useCaseLabels = result.useCaseLabels;
                $scope.scenarioLabels = result.scenarioLabels;

                $scope.hasAnyLabels = function () {
                    var hasAnyUseCaseLabels = $scope.useCaseLabels.labels.length > 0;
                    var hasAnyScenarioLabels = $scope.scenarioLabels.labels.length > 0;
                    var hasAnyStepLabels = $scope.step.stepDescription.labels.labels.length > 0;
                    var hasAnyPageLabels = $scope.step.page.labels.labels.length > 0;

                    return hasAnyUseCaseLabels || hasAnyScenarioLabels || hasAnyStepLabels || hasAnyPageLabels;
                };
            }
        );
    }

    function bindStepNavigation() {

        GlobalHotkeysService.registerPageHotkeyCode(37, function () {
            // left arrow
            $scope.goToPreviousStep();
        });
        GlobalHotkeysService.registerPageHotkeyCode(39, function () {
            // right arrow
            $scope.goToNextStep();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+37', function () {
            // control + left arrow
            $scope.goToPreviousPage();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+39', function () {
            // control + right arrow
            $scope.goToNextPage();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+36', function () {
            // control + Home
            $scope.goToFirstStep();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+35', function () {
            // control + down arrow
            $scope.goToLastStep();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+38', function () {
            // control + up arrow
            $scope.goToPreviousVariant();
        });
        GlobalHotkeysService.registerPageHotkeyCode('ctrl+40', function () {
            // control + down arrow
            $scope.goToNextVariant();
        });

        $scope.isFirstStep = function () {
            return $scope.stepNavigation && $scope.stepNavigation.stepIndex === 0;
        };

        $scope.isLastStep = function () {
            return $scope.stepNavigation && $scope.stepNavigation.stepIndex === $scope.stepStatistics.totalNumberOfStepsInScenario - 1;
        };

        $scope.isFirstPage = function () {
            return $scope.stepNavigation && $scope.stepNavigation.pageIndex === 0;
        };

        $scope.isLastPage = function () {
            return $scope.stepNavigation && $scope.stepNavigation.pageIndex === $scope.stepStatistics.totalNumberOfPagesInScenario - 1;
        };

        $scope.goToPreviousStep = function () {
            if (!$scope.stepNavigation || !$scope.stepNavigation.previousStep) {
                return;
            }
            $scope.go($scope.stepNavigation.previousStep);
        };

        $scope.goToNextStep = function () {
            if (!$scope.stepNavigation || !$scope.stepNavigation.nextStep) {
                return;
            }
            $scope.go($scope.stepNavigation.nextStep);
        };

        $scope.goToPreviousPage = function () {
            if (!$scope.stepNavigation || !$scope.stepNavigation.previousPage) {
                return;
            }
            $scope.go($scope.stepNavigation.previousPage);
        };

        $scope.goToNextPage = function () {
            if (!$scope.stepNavigation || !$scope.stepNavigation.nextPage) {
                return;
            }
            $scope.go($scope.stepNavigation.nextPage);
        };

        $scope.getCurrentStepIndexForDisplay = function () {
            if (angular.isUndefined($scope.stepNavigation)) {
                return '?';
            }
            return $scope.stepNavigation.stepIndex + 1;
        };

        $scope.getCurrentPageIndexForDisplay = function () {
            if (angular.isUndefined($scope.stepNavigation)) {
                return '?';
            }
            return $scope.stepNavigation.pageIndex + 1;
        };

        $scope.getStepIndexInCurrentPageForDisplay = function () {
            if (angular.isUndefined($scope.stepNavigation)) {
                return '?';
            }
            return $scope.stepNavigation.stepInPageOccurrence + 1;
        };

        $scope.getNumberOfStepsInCurrentPageForDisplay = function () {
            if (angular.isUndefined($scope.stepStatistics)) {
                return '?';
            }
            return $scope.stepStatistics.totalNumberOfStepsInPageOccurrence;
        };
    }

    var mode1 = true;
    var mode2 = false;
    $scope.goToMode1 = function () {
        mode1 = true;
        mode2 = false;
    };

    $scope.goToMode2 = function () {
        mode1 = false;
        mode2 = true;
    };

    $scope.isMode1 = function () {
        return mode1;
    };

    $scope.isMode2 = function () {
        return mode2;
    };

    // This URL is only used internally, not for sharing
    $scope.getScreenShotUrl = function () {
        if (angular.isUndefined($scope.step)) {
            return;
        }

        var imageName = $scope.step.stepDescription.screenshotFileName;

        if (angular.isUndefined(imageName)) {
            return;
        }

        var selected = SelectedBranchAndBuild.selected();
        return HostnameAndPort.forLink() + 'rest/branch/' + selected.branch + '/build/' + selected.build + '/usecase/' + $scope.stepIdentifier.usecaseName + '/scenario/' + $scope.stepIdentifier.scenarioName + '/image/' + imageName;
    };

    // This URL is only used internally, not for sharing
    $scope.getScreenShotUrl2 = function () {
        if (angular.isUndefined($scope.step)) {
            return;
        }

        var imageName = $scope.step.stepDescription.screenshotFileName;

        if (angular.isUndefined(imageName)) {
            return;
        }

        var selected = SelectedBranchAndBuild.selectedForComparison();
        return HostnameAndPort.forLink() + 'rest/branch/' + selected.branch + '/build/' + selected.build + '/usecase/' + $scope.stepIdentifier.usecaseName + '/scenario/' + $scope.stepIdentifier.scenarioName + '/image/' + imageName;
    };


    $scope.go = function (step) {
        $location.path('/step/' + (step.useCaseName || useCaseName) + '/' + (step.scenarioName || scenarioName) + '/' + step.pageName + '/' + step.pageOccurrence + '/' + step.stepInPageOccurrence + '/compare');
    };

    $scope.getCurrentUrlForSharing = function () {
        return $location.absUrl() + createLabelUrl('&', getAllLabels());
    };

    $scope.getCurrentUrl = function () {
        return $location.absUrl();
    };

    var getAllLabels = function () {
        var labels = [];
        if ($scope.useCaseLabels && $scope.scenarioLabels && $scope.step) {
            labels = labels.concat($scope.useCaseLabels.labels).concat($scope.scenarioLabels.labels).concat($scope.step.stepDescription.labels.labels).concat($scope.step.page.labels.labels);
        }
        return labels;
    };

    var createLabelUrl = function (prefix, labels) {
        if (angular.isUndefined(labels) || !angular.isArray(labels) || labels.length === 0) {
            return '';
        }

        return prefix + 'labels=' + labels.map(encodeURIComponent).join();
    };


    $scope.getScreenCompare = function () {
        var img1url = $scope.getScreenShotUrl();
        var img2url = $scope.getScreenShotUrl2();
        var diffimgurl;
        if ($scope.isMode1()) {
            return;
        }
        if (typeof img1url === 'undefined' || typeof img2url === 'undefined') {
            return;
        }
        else {
            var imgDiff = resemble(img1url).compareTo(img2url).onComplete(function (data) {
                console.log(data);
                //console.log(data.getImageDataUrl());
                diffimgurl = data.getImageDataUrl();
            });
            return diffimgurl;
        }

    };

    $scope.initCompare = function () {
        $('#image-diff-m2').twentytwenty();
    };

});
