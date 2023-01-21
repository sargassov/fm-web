angular.module('match_result', ['ngStorage']).controller('match_resultController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getActualDate = function () {
        $http.get(contextPath + '/dates')
            .then(function successCallback(response) {
                $scope.today = response.data;
            }, function errorCallback() {
                alert('PRESENT DAY NOT FOUND');
            });
    };

    $scope.loadResults = function () {
        $http.get(contextPath + '/match/post_info', $scope.today)
            .then(function successCallback(response) {
                $scope.matchInfo = response.data;
            }, function errorCallback() {
                alert('MATCH INFO NOT LOADED');
            });
    };

    $scope.getActualDate();
    $scope.loadResults();
});