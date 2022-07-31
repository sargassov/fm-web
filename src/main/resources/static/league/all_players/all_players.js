angular.module('all_players', ['ngStorage']).controller('all_playersController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.loadPlayersSort = function (parameter) {
        $http.get(contextPath + '/league/players/' + parameter)
            .then(function successCallback(response) {
                $scope.Players = response.data;
            }, function errorCallback(response) {
                alert('PLAYERS NOT FOUND');
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
    $scope.loadPlayersSort(0);
});