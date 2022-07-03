angular.module('split_sectors', ['ngStorage']).controller('split_sectorsController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';


    $scope.loadCurrentSectorsInfo = function () {
        $http.get(contextPath + '/stadium/sectors/info')
            .then(function successCallback(response) {
                $scope.Information = response.data;
            }, function errorCallback(response) {
                alert('CURRENT SECTORS INFO NOT FOUND');
            });
    };

    $scope.loadSectorCapacityInformation = function () {
        $http.get(contextPath + '/stadium/sectors/info/all')
            .then(function successCallback(response) {
                $scope.SectorCapacityInformation = response.data;
            }, function errorCallback(response) {
                alert('SECTORS CAPACITY INFORMATION NOT FOUND');
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

    $scope.changeSectorCapacity = function (type, delta) {
        var obj = {type: type, value: delta}
        $http.put(contextPath + '/stadium/sectors/change', obj)
            .then(function successCallback(response) {
                $scope.loadCurrentSectorsInfo();
                $scope.loadSectorCapacityInformation();
                $scope.getUserTeamName();
            }, function errorCallback(response) {
                alert('CANT CHANGE CAPACITY');
            });
    };

    $scope.getActualDate();
    $scope.getUserTeamName();
    $scope.loadCurrentSectorsInfo();
    $scope.loadSectorCapacityInformation();

});