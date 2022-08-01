angular.module('banks', ['ngStorage']).controller('banksController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:7777/fm';

    $scope.getStartMessage = function () {
        $http.get(contextPath + '/team/finance/start_message')
            .then(function successCallback(response) {
                $scope.start_message = response.data;
            }, function errorCallback(response) {
                alert('START MESSAGE NOT FOUND');
            });
    };

    $scope.loanCurrentLoans = function () {
        $http.get(contextPath + '/team/finance/load_loans')
            .then(function successCallback(response) {
                $scope.Loans = response.data;
            }, function errorCallback(response) {
                alert('CANT LOAD CURRENT LOANS');
            });
    };


    $scope.payTheLoan= function (loan) {
        $http.post(contextPath + '/team/finance/reamin_loan', loan)
            .then(function successCallback(response) {
                window.location.reload();
            }, function errorCallback(response) {
                alert('CANT REMAIN THIS LOAN');
            });
    };

    $scope.canTakeMore = function () {
        if ($scope.start_message.toString().endsWith("more!")){
            return false
        }
        return true;
    };

    $scope.isClicked = function () {
        if($scope.potencialLoans == null){
            return false
        }
        return true;
    };

    $scope.loanRequest = function () {
        $http.get(contextPath + '/banks/loan_request/' + $scope.loan.request)
            .then(function successCallback(response) {
                $scope.potencialLoans = response.data;
            }, function errorCallback(response) {
                alert('CANT GET ANSWER WITH LOAN INFORMATION');
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

    $scope.takeNewLoan = function (loan, coeff) {
        if (coeff == 1) {
            loan.typeOfReturn = "PER_DAY";
        } else if  (coeff == 2) {
            loan.typeOfReturn = "PER_WEEK";
        } else {
            loan.typeOfReturn = "PER_MONTH";
        }

        $http.post(contextPath + '/banks/finance/get', loan)
            .then(function successCallback(response) {
                window.location.reload();
            }, function errorCallback(response) {
                alert('LOAN ' + loan + ' NOT FOUND');
            });
    };


    $scope.loanCurrentLoans();
    $scope.getStartMessage();
    $scope.getActualDate();
    $scope.getUserTeamName();
});