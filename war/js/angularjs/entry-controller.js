function EntryController($scope, $http) {
	$scope.serverUpdateRunning = false;
	$scope.serverUpdateSuccessful = false;
	$scope.serverUpdateFailed = false;

	$scope.cancel = function() {
		$scope.form = angular.copy($scope.entry);
	}

	$scope.save = function() {
		/*
		var companyName = '';
		if ($scope.form.company) {
			companyName = $scope.form.company.name;
		}
		*/

		var text = '';
		if ($scope.form.text) {
			text = $scope.form.text.value;
		}

		var EntryFormData = {
			'entry[keyDigest]' : $scope.form.keyDigest,
			'entry[text]' : text
		};

		var url = '';
		if ($scope.form.keyDigest)
			url = '/user/edit/entry';
		else
			url = '/user/add/entry';

		$scope.setServerUpdateRunningTrue();
		$scope.reportActionForAnalytics(url);

		$http({
			method : 'POST',
			url : url,
			data : $.param(EntryFormData),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data, status, headers, config) {
			$scope.entry = $scope.form;
			// if add case update keyDigest got as response
			if (!$scope.entry.keyDigest) {
				$scope.entry.keyDigest = data;
				$scope.updateGlobalEntry($scope.entry);
			}
			$scope.cancel();

			$scope.setServerUpdateSuccessfulTrue(data);
		}).error(function(data, status, headers, config) {
			// TODO
			$scope.setServerUpdateFailedTrue(data);
		});
	}

	$scope.setServerUpdateRunningTrue = function() {
		$scope.serverUpdateRunning = true;
		$scope.serverUpdateSuccessful = false;
		$scope.serverUpdateFailed = false;
		$scope.setGlobalServerUpdateRunningTrue();
	}

	$scope.setServerUpdateSuccessfulTrue = function(data) {
		$scope.serverUpdateRunning = false;
		$scope.serverUpdateSuccessful = true;

		$scope.setServerUpdateSuccessfulMessage(data);
	}

	$scope.setServerUpdateFailedTrue = function(data) {
		$scope.serverUpdateRunning = false;
		$scope.serverUpdateFailed = true;

		$scope.setServerUpdateFailedMessage(data);
	}

	$scope.isCancelDisabled = function() {
		return $scope.isGlobalServerUpdateRunning() || angular.equals($scope.entry, $scope.form);
	}

	$scope.isSaveDisabled = function() {
		return $scope.isGlobalServerUpdateRunning() || $scope.EntryForm.$invalid || angular.equals($scope.entry, $scope.form);
	}

	$scope.moveUp = function() {
		var EntryMoveUpData = {
			'entry[keyDigest]' : $scope.entry.keyDigest,
			'entry[direction]' : 'up',
		};

		var url = '/user/move/entry';

		$scope.setServerUpdateRunningTrue();
		$scope.reportActionForAnalytics(url);

		$http({
			method : 'POST',
			url : url,
			data : $.param(EntryMoveUpData),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data, status, headers, config) {
			$scope.moveUpEntry($scope.entry);

			$scope.setServerUpdateSuccessfulTrue(data);
		}).error(function(data, status, headers, config) {
			// TODO
			$scope.setServerUpdateFailedTrue(data);
		});
	}

	$scope.moveDown = function() {
		var EntryMoveDownData = {
			'entry[keyDigest]' : $scope.entry.keyDigest,
			'entry[direction]' : 'down',
		};

		var url = '/user/move/entry';

		$scope.setServerUpdateRunningTrue();
		$scope.reportActionForAnalytics(url);

		$http({
			method : 'POST',
			url : url,
			data : $.param(EntryMoveDownData),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data, status, headers, config) {
			$scope.moveDownEntry($scope.entry);

			$scope.setServerUpdateSuccessfulTrue(data);
		}).error(function(data, status, headers, config) {
			// TODO
			$scope.setServerUpdateFailedTrue(data);
		});
	}

	$scope.isMoveUpDisabled = function() {
		if ($scope.isGlobalServerUpdateRunning() || $scope.hasUnsavedEntry())
			return true;
		var index = $scope.indexOfEntry($scope.entry);
		if (index == 0)
			return true;
		return false;
	}

	$scope.isMoveDownDisabled = function() {
		if ($scope.isGlobalServerUpdateRunning() || $scope.hasUnsavedEntry())
			return true;
		var index = $scope.indexOfEntry($scope.entry);
		if (index == $scope.lastIndexOfEntry())
			return true;
		return false;
	}

	$scope.isMoveUpHidden = function() {
		return $scope.numberOfEntries() < 2;
	}

	$scope.isMoveDownHidden = function() {
		return $scope.numberOfEntries() < 2;
	}

	$scope.remove = function() {
		if (!$scope.entry.keyDigest) {
			$scope.removeEntry($scope.entry);
			return;
		}

		var EntryRemoveData = {
			'entry[keyDigest]' : $scope.entry.keyDigest
		};

		var url = '/user/delete/entry';

		$scope.setServerUpdateRunningTrue();
		$scope.reportActionForAnalytics(url);

		$http({
			method : 'POST',
			url : url,
			data : $.param(EntryRemoveData),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data, status, headers, config) {
			$scope.removeEntry($scope.entry);

			$scope.setServerUpdateSuccessfulTrue(data);
		}).error(function(data, status, headers, config) {
			// TODO
			$scope.setServerUpdateFailedTrue(data);
		});
	}

	$scope.isRemoveDisabled = function() {
		if ($scope.isGlobalServerUpdateRunning())
			return true;
		return false;
	}

	// initialize
	$scope.$on('resetContentEditableModel', function() {
		$scope.entry.text.value = $scope.form.text.value;
	});

	$scope.cancel();
}
