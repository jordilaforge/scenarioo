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

angular.module('scenarioo.services').factory('ScCompareStepPopup', function (localStorageService, $modal) {

    // This is required to avoid multiple popups (they could be opened using keyboard shortcuts)
    var modalIsCurrentlyOpen = false;

    function showCompareStepPopup() {
        if (modalIsCurrentlyOpen === true) {
            return;
        }

        modalIsCurrentlyOpen = true;

        var modalInstance = $modal.open({
            templateUrl: 'views/compareStepPopup.html',
            controller: 'ScCompareStepPopupController',
            windowClass: 'modal-small',
            backdropFade: true
        });

        modalInstance.result['finally'](function () {
            modalIsCurrentlyOpen = false;
        });
    }

    return {
        showCompareStepPopup: showCompareStepPopup
    };

}).controller('ScCompareStepPopupController', function ($filter, $location, $scope, $modalInstance, $routeParams, localStorageService, BranchesAndBuilds, BranchesAndBuildsForComparison, SelectedBranchAndBuild) {

    var useCaseName = $routeParams.useCaseName;
    var scenarioName = $routeParams.scenarioName;
    var pageName = $routeParams.pageName;
    var pageOccurrence = $routeParams.pageOccurrence;
    var stepInPageOccurrence = $routeParams.stepInPageOccurrence;

    loadBranchesAndBuilds();
    loadBranchesAndBuildsForComparison();

    function loadBranchesAndBuilds() {
        BranchesAndBuilds.getBranchesAndBuilds().then(function onSuccess(branchesAndBuilds) {
                $scope.branchesAndBuilds = branchesAndBuilds;
            }
        );
    }

    function loadBranchesAndBuildsForComparison() {
        BranchesAndBuildsForComparison.getBranchesAndBuildsForComparison().then(function onSuccess(branchesAndBuildsForComparison) {
                $scope.branchesAndBuildsForComparison = branchesAndBuildsForComparison;
            }
        );
    }

    $scope.setBranchForComparison = function (branchCompare) {
        $scope.branchesAndBuildsForComparison.selectedBranchForComparison = branchCompare;
        localStorageService.remove(SelectedBranchAndBuild.COMPARE_BUILD_KEY);
        $location.search(SelectedBranchAndBuild.COMPARE_BRANCH_KEY, branchCompare.branch.name);
    };

    $scope.setBuildForComparison = function (selectedBranchForComparison, buildCompare) {
        $scope.branchesAndBuildsForComparison.selectedBuildForComparison = buildCompare;
        $location.search(SelectedBranchAndBuild.COMPARE_BUILD_KEY, buildCompare.linkName);
    };

    $scope.getDisplayName = function (buildCompare) {
        if (angular.isUndefined(buildCompare)) {
            return '';
        }

        if (angular.isDefined(buildCompare.displayName) && buildCompare.displayName !== null) {
            return buildCompare.displayName;
        }

        if ($scope.isBuildAlias(buildCompare)) {
            return buildCompare.linkName;
        } else {
            return 'Revision: ' + buildCompare.build.revision;
        }
    };

    $scope.isBuildAlias = function (buildCompare) {
        if (angular.isUndefined(buildCompare)) {
            return false;
        }

        return buildCompare.build.name !== buildCompare.linkName;
    };

    $scope.startCompare = function () {
        $modalInstance.dismiss('close');
        if (window.location.href.indexOf('/step/') > -1) {
            $location.path('/step/' + encodeURIComponent(useCaseName) + '/' + encodeURIComponent(scenarioName) + '/' + encodeURIComponent(pageName) +
            '/' + pageOccurrence + '/' + stepInPageOccurrence + '/compare/');
        } else if (window.location.href.indexOf('/scenario/') > -1) {
            $location.path('/scenario/' + encodeURIComponent(useCaseName) + '/' + encodeURIComponent(scenarioName) + '/compare');
        }
    };

    $scope.close = function () {
        $modalInstance.dismiss('cancel');
    };

});
