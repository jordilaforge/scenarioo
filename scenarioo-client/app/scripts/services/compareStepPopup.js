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

}).controller('ScCompareStepPopupController', function ($location, $scope, $modalInstance, localStorageService, BranchesAndBuilds, SelectedBranchAndBuild) {

  loadBranchesAndBuilds();

  function loadBranchesAndBuilds() {
    BranchesAndBuilds.getBranchesAndBuilds().then(function onSuccess(branchesAndBuilds) {
        $scope.branchesAndBuilds = branchesAndBuilds;
      }, function onFailure(error) {
        console.log(error);
      }
    );
  }

  $scope.setBranchForComparison = function (branch) {
    $scope.branchesAndBuilds.selectedBranchForComparison = branch;
  };

  $scope.setBuildForComparison = function (selectedBranchForComparison, build) {
    $scope.branchesAndBuilds.selectedBuildForComparison = build;
  };

  $scope.getDisplayName = function (build) {
    if (angular.isUndefined(build)) {
      return '';
    }

    if(angular.isDefined(build.displayName) && build.displayName !== null) {
      return build.displayName;
    }

    if ($scope.isBuildAlias(build)) {
      return build.linkName;
    } else {
      return 'Revision: ' + build.build.revision;
    }
  };

  $scope.isBuildAlias = function (build) {
    if (angular.isUndefined(build)) {
      return false;
    }

    return build.build.name !== build.linkName;
  };

  $scope.compare = function () {

  };

  $scope.close = function () {
    $modalInstance.dismiss('cancel');
  };

});
