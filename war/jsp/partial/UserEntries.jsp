Entries:
<button ng-click="addEntry()" ng-disabled="isAddEntryDisabled()" class="btn btn-info" id="position-add-button">+</button>
<div ng-repeat="entry in userprofile.entries" ng-controller="EntryController" ng-animate="'repeat'">
	<form name="EntryForm" novalidate>
		<span class="process-indicator {{getServerUpdateStatus(serverUpdateRunning, serverUpdateSuccessful, serverUpdateFailed)}}"></span>

		<div class="tobevalidated-control">
			<div contenteditable="true" class='textarea' data-placeholder='Short Description' ng-model="form.text.value" ng-maxlength="2000" ng-required="false"></div>
		</div>

		<div class='actions' id="position-section-actions">
			<button ng-click="moveUp()" ng-disabled="isMoveUpDisabled()" ng-hide="isMoveUpHidden()" class="btn btn-primary" data-attr='Move Up'>^</button>
			<button ng-click="moveDown()" ng-disabled="isMoveDownDisabled()" ng-hide="isMoveDownHidden()" class="btn btn-primary" data-attr='Move Down'>v</button>
			<button ng-click="remove()" ng-disabled="isRemoveDisabled()" class="btn btn-danger" data-attr="Remove">x</button>

			<button ng-click="cancel()" ng-disabled="isCancelDisabled()" class="btn btn-warning" data-attr="Reset">reset</button>
			<button ng-click="save()" ng-disabled="isSaveDisabled()" class="btn btn-success" data-attr="Save">save</button>
		</div>
	</form>
</div>