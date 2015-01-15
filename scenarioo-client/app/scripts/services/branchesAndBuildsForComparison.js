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

angular.module('scenarioo.services').factory('BranchesAndBuildsForComparison', function ($rootScope, Config, BranchesResource, $q, SelectedBranchAndBuild) {

    var selected;
    var index;

    var branchesAndBuildsForComparisonData = function () {
        var deferred = $q.defer();
        BranchesResource.query({}, function findSelectedBranchAndBuild(branches) {
            if (branches.length === 0) {
                deferred.reject('Branch list empty!');
                return;
            }

            var loadedData = {
                branches: branches
            };

            if (SelectedBranchAndBuild.isDefinedForComparison()) {
                selected = SelectedBranchAndBuild.selectedForComparison();

                for (index = 0; index < loadedData.branches.length; index++) {
                    if (loadedData.branches[index].branch.name === selected.branch) {
                        loadedData.selectedBranchForComparison = loadedData.branches[index];
                    }
                }

                if (angular.isDefined(loadedData.selectedBranchForComparison)) {
                    var allBuildsOnSelectedBranch = loadedData.selectedBranchForComparison.builds;
                    for (index = 0; index < loadedData.selectedBranchForComparison.builds.length; index++) {
                        if (allBuildsOnSelectedBranch[index].linkName === selected.build) {
                            loadedData.selectedBuildForComparison = allBuildsOnSelectedBranch[index];
                        }
                    }
                }
            }

            deferred.resolve(loadedData);
        }, function (error) {
            deferred.reject(error);
        });

        return deferred.promise;
    };

    return {
        getBranchesAndBuildsForComparison: branchesAndBuildsForComparisonData
    };
});
