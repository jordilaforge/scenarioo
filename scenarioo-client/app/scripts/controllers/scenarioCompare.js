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

angular.module('scenarioo.controllers').controller('ScenarioCompareCtrl', function ($scope, BranchesAndBuilds, SelectedBranchAndBuild) {

  loadBranchesAndBuilds();
  //loadSelectedBranchAndBuild();

  function loadBranchesAndBuilds() {
    BranchesAndBuilds.getBranchesAndBuilds().then(function onSuccess(branchesAndBuilds) {
        $scope.branchesAndBuilds = branchesAndBuilds;
      }, function onFailure(error) {
        console.log(error);
      }
    );
  }

  /*function loadSelectedBranchAndBuild() {
    SelectedBranchAndBuild.getBranchAndBuildForComparison().then(function onSuccess(selectedBranchAndBuild) {
        $scope.selectedBranchAndBuild = selectedBranchAndBuild;
      }, function onFailure(error) {
        console.log(error);
      }
    );
  }*/

});
