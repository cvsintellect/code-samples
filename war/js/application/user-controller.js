function UserController($scope, userProfileData) {
	var hasAtleastOneSectionEntry = false;

	// entries
	$scope.addEntry = function() {
		if (!$scope.userprofile.entries) {
			$scope.userprofile.entries = new Array();
		}
		var entry = new Object();
		entry.text = new Object();
		entry.text.value = '';
		$scope.userprofile.entries.unshift(entry);
	}

	$scope.isAddEntryDisabled = function() {
		return $scope.hasUnsavedEntry();
	}

	$scope.hasAtleastOneEntry = function() {
		if ($scope.numberOfEntries() > 0)
			return true;
		return false;
	}

	$scope.numberOfEntries = function() {
		if ($scope.userprofile.entries && $scope.userprofile.entries.length)
			return $scope.userprofile.entries.length;
		return 0;
	}

	$scope.hasUnsavedEntry = function() {
		if ($scope.userprofile.entries && $scope.userprofile.entries.length > 0) {
			for ( var i = 0; i < $scope.userprofile.entries.length; i++) {
				if (!$scope.userprofile.entries[i].keyDigest)
					return true;
			}
		}
		return false;
	}

	$scope.indexOfEntry = function(entry) {
		if (!entry.keyDigest)
			return 0;

		if ($scope.userprofile.entries && $scope.userprofile.entries.length > 0) {
			for ( var i = 0; i < $scope.userprofile.entries.length; i++) {
				if ($scope.userprofile.entries[i].keyDigest == entry.keyDigest)
					return i;
			}
		}
		return -1;
	}

	$scope.lastIndexOfEntry = function() {
		if ($scope.userprofile.entries) {
			return $scope.userprofile.entries.length - 1;
		}
		return -1;
	}

	$scope.moveUpEntry = function(entry) {
		var index = $scope.indexOfEntry(entry);
		if (index > 0) {
			$scope.userprofile.entries.move(index, index - 1);
		}
	}

	$scope.moveDownEntry = function(entry) {
		var index = $scope.indexOfEntry(entry);
		if (index < $scope.userprofile.entries.length - 1) {
			$scope.userprofile.entries.move(index, index + 1);
		}
	}

	$scope.removeEntry = function(entry) {
		var index = $scope.indexOfEntry(entry);
		if (0 <= index && index < $scope.userprofile.entries.length) {
			$scope.userprofile.entries.splice(index, 1);
		}
	}

	$scope.updateGlobalEntry = function(entry) {
		$scope.userprofile.entries[0] = entry;
	}

	$scope.getServerUpdateStatus = function(serverUpdateRunning, serverUpdateSuccessful, serverUpdateFailed) {
		if (serverUpdateRunning == true)
			return "saving";
		if (serverUpdateSuccessful == true)
			return "saved";
		if (serverUpdateFailed == true)
			return "failed";
		return "hidden";
	}

	// message
	$scope.setServerUpdateSuccessfulMessage = function(message) {
		$scope.message.serverUpdateSuccessful = message;
		$scope.message.serverUpdateFailed = '';
		$scope.setGlobalServerUpdateRunningFalse();
	}

	$scope.isServerUpdateSuccessfulMessageAvailable = function() {
		if ($scope.message.serverUpdateSuccessful && $scope.message.serverUpdateSuccessful != "")
			return true;
		return false;
	}

	$scope.setServerUpdateFailedMessage = function(message) {
		$scope.message.serverUpdateFailed = message;
		$scope.message.serverUpdateSuccessful = '';
		$scope.setGlobalServerUpdateRunningFalse();
	}

	$scope.isServerUpdateFailedMessageAvailable = function() {
		if ($scope.message.serverUpdateFailed && $scope.message.serverUpdateFailed != "")
			return true;
		return false;
	}

	// global
	$scope.setGlobalServerUpdateRunningTrue = function() {
		$scope.globaldata.serverUpdateRunning = true;
	}

	$scope.setGlobalServerUpdateRunningFalse = function() {
		$scope.globaldata.serverUpdateRunning = false;
	}

	$scope.isGlobalServerUpdateRunning = function() {
		return $scope.globaldata.serverUpdateRunning;
	}

	// google analytics
	$scope.reportActionForAnalytics = function(actionURL) {
		try {
			_gaq.push([ '_trackPageview', actionURL ]);
		} catch (e) {
		}
	}

	// initialize
	$scope.userprofile = userProfileData;

	// put empty content for unavailable data

	// message
	$scope.message = new Object();
	$scope.message.serverUpdateSuccessful = "";
	$scope.message.serverUpdateFailed = "";

	// global data
	$scope.globaldata = new Object();
	$scope.globaldata.serverUpdateRunning = false;
}
