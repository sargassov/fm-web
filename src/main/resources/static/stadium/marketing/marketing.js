angular.module('marketing', ['ngStorage']).controller('marketingController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadCurrentMarketsInfo = function () {
        $http.get(contextPath + '/team/markets/info')
            .then(function successCallback(response) {
                $scope.Information = response.data;
                $http.get(contextPath + '/team/markets/show')
                    .then(function successCallback(response) {
                        $scope.showCondition = response.data;
                    }, function errorCallback() {
                        alert('SHOW CONDITION NOT LOADED');
                    });
            }, function errorCallback() {
                alert('CURRENT MARKETS INFO NOT FOUND');
            });
    };

    $scope.canTakeMore = function () {
        if ($scope.showCondition){
            return true
        }
        return false;
    };

    $scope.loadPotencialMarketPrograms = function () {
        $http.get(contextPath + '/team/market/programs')
            .then(function successCallback(response) {
                $scope.potencialMarkets = response.data;
            }, function errorCallback(response) {
                alert('CANT LOAD ALL POTENCIAL MARKET PROGRAMS');
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

    $scope.getUserTeamName = function () {
        $http.get(contextPath + '/team/name')
            .then(function successCallback(response) {
                $scope.team = response.data;
            }, function errorCallback(response) {
                alert('TEAM NAME NOT FOUND');
            });
    };

    $scope.takeMarketProgram = function (type, coeff) {
        var obj = {type: type, value: coeff}
        $http.post(contextPath + '/team/market/addNew', obj)
            .then(function successCallback(response) {
                $scope.getUserTeamName();
                $scope.loadCurrentMarketsInfo();
            }, function errorCallback(response) {
                alert('CANT ADD NEW MARKET PROGRAM');
            });
    };

    $scope.rejectProgram = function (type) {
        $http.post(contextPath + '/team/market/reject', type)
            .then(function successCallback(response) {
                $scope.getUserTeamName();
                $scope.loadCurrentMarketsInfo();
            }, function errorCallback(response) {
                alert('CANT REJECT THE PROGRAM');
            });
    };

    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.loadCurrentMarketsInfo();
    $scope.loadPotencialMarketPrograms();
});