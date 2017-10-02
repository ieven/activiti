var UITree = function () {

    var contextualMenuSample = function() {

        $("#tree_3").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                "check_callback" : true,
                'data': [{
                		"id": "1",
                        "text": "Parent Node",
                        "children": [{
                        	"id": "2",
                            "text": "Initially selected"
                        }, {
                        	"id": "3",
                            "text": "Custom Icon",
                        }, {
                            "text": "Initially open",
                            "children": [
                                {"text": "Another node"}
                            ]
                        }, {
                            "text": "Another Custom Icon",
                        }, {
                            "text": "Disabled Node",
                        }, {
                            "text": "Sub Nodes",
                            "children": [
                                {"text": "Item 1", "icon" : "fa fa-file icon-state-warning"},
                                {"text": "Item 2", "icon" : "fa fa-file icon-state-success"},
                                {"text": "Item 3", "icon" : "fa fa-file icon-state-default"},
                                {"text": "Item 4", "icon" : "fa fa-file icon-state-danger"},
                                {"text": "Item 5", "icon" : "fa fa-file icon-state-info"}
                            ]
                        }]
                    },
                    "Another Node"
                ]
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder icon-state-warning icon-lg"
                },
                "file" : {
                    "icon" : "fa fa-file icon-state-warning icon-lg"
                }
            },
            "state" : { "key" : "demo2" },
            "plugins" : [ "contextmenu", "dnd", "state", "types" ]
        });
    
    }

    return {
        init: function () {

            contextualMenuSample();

        }

    };

}();