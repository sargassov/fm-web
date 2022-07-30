angular.module('league_table', ['ngStorage']).controller('league_tableController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadTeamTable = function () {
        $http.get(contextPath + '/league/table')
            .then(function successCallback(response) {
                $scope.Teams = response.data;
            }, function errorCallback(response) {
                alert('CANT LOAD TEAM TABLE');
            });
    };

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };


    $scope.getActualDate();
    $scope.loadTeamTable();
});