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

angular.module('scenarioo.controllers').controller('ScenarioCompareCtrl', function ($scope, $routeParams,HostnameAndPort, SelectedBranchAndBuild, BranchesAndBuilds, BranchesAndBuildsForComparison, ScenarioCompareResource) {

    var useCaseName = $routeParams.useCaseName.replace(/%20/g, ' ' );
    var scenarioName = $routeParams.scenarioName;
    var selectedBranchAndBuild;
    var selectedBranchAndBuildForComparison;

    loadBranchesAndBuilds();
    loadBranchesAndBuildsForComparison();
    loadData(SelectedBranchAndBuild.selected(),SelectedBranchAndBuild.selectedForComparison());

    function loadData(selected,selectedForComparison) {
        selectedBranchAndBuild = selected;
        selectedBranchAndBuildForComparison = selectedForComparison;
        ScenarioCompareResource.get(
            {
                branchName: selected.branch,
                buildName: selected.build,
                compareBranch: selectedForComparison.branch,
                compareBuild: selectedForComparison.build,
                usecaseName: useCaseName,
                scenarioName: scenarioName

            },

            function(result) {
                $scope.resultList = result;
            });
        $scope.imageURL = HostnameAndPort.forLink();
    }

    function loadBranchesAndBuilds() {
        BranchesAndBuilds.getBranchesAndBuilds().then(function onSuccess(branchesAndBuilds) {
            $scope.branchesAndBuilds = branchesAndBuilds;
        }, function onFailure(error) {
            console.log(error);
        }
        );
    }

    function loadBranchesAndBuildsForComparison() {
        BranchesAndBuildsForComparison.getBranchesAndBuildsForComparison().then(function onSuccess(branchesAndBuildsForComparison) {
            $scope.branchesAndBuildsForComparison = branchesAndBuildsForComparison;
        }, function onFailure(error) {
            console.log(error);
        }
        );
    }

    function MyCtrl($scope, $timeout) {
        var path = 'https://si0.twimg.com/profile_images/';
        $timeout(function () {
        $scope.path = path + '2149314222/square.png';
        }, 1000);
    }
});
