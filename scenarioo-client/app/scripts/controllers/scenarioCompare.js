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

angular.module('scenarioo.controllers').controller('ScenarioCompareCtrl', function ($scope, $routeParams, SelectedBranchAndBuild, BranchesAndBuilds, BranchesAndBuildsForComparison, ScenarioCompareResource) {

    var useCaseName = $routeParams.useCaseName;
    var scenarioName = $routeParams.scenarioName;
    var selectedBranchAndBuild;
    var selectedBranchAndBuildForComparison;

    loadBranchesAndBuilds();
    loadBranchesAndBuildsForComparison();
    loadData(SelectedBranchAndBuild.selected(),SelectedBranchAndBuild.selectedForComparison());
    console.log(scenarioName);
    console.log(useCaseName);
    console.log(selectedBranchAndBuild.branch);
    console.log(selectedBranchAndBuild.build);

    function loadData(selected,selectedForComparison) {
        selectedBranchAndBuild = selected;
        selectedBranchAndBuildForComparison = selectedForComparison;
        console.log(selectedBranchAndBuildForComparison);
        console.log(selectedBranchAndBuild);
        ScenarioCompareResource.get(
            {
                branchName: selected.branch,
                buildName: selected.build,
                usecaseName: useCaseName,
                scenarioName: scenarioName,
                compareBranch: selectedForComparison.branch,
                compareBuild: selectedForComparison.build
            },
            function(result) {
                $scope.scenarioName = result.scenarioName;
                $scope.usecaseName = result.usecaseName;
                $scope.branchName = result.branchName;
                $scope.buildName = result.buildName;
                $scope.compareBranch = result.compareBranch;
                $scope.compareBuild = result.compareBuild;
                $scope.pageList = result.pageList;
                $scope.stepList = result.pageList.stepList;
            });
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

});
