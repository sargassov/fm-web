angular.module('match', ['ngStorage']).controller('matchController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback() {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.getUserTeamNameAndOpponentName = function () {
        $http.get(contextPath + '/match/pre_info')
            .then(function successCallback(response) {
                $scope.matchInfo = response.data;
            }, function errorCallback() {
                alert('MATCH INFO NOT LOADED');
            });
    };

    $scope.imitate = function () {
        $http.post(contextPath + '/match/imitate', $scope.today)
            .then(function successCallback() {
                window.location.href = '../match_result/match_result.html';
            }, function errorCallback() {
                alert('NOT LOADED');
            });
    };

    $scope.getActualDate();
    $scope.getUserTeamNameAndOpponentName();
});