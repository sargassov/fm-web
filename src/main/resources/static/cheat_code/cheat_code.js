angular.module('cheat_code', ['ngStorage']).controller('cheat_codeController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback(response) {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback() {
                alert('TEAM NAME NOT FOUND');d
            });
    };

    $scope.useCheat = function () {
        $http.post(contextPath + '/cheat', $scope.cheater)
            .then(function successCallback(response) {
                $scope.Answer = response.data;
            }, function errorCallback(response) {
                alert('SOMETHING WERE WRONG');
            });
    };


    $scope.getActualDate();
    $scope.getUserTeamName();
});