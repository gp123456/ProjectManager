    function clearDates(obj)
    {
        obj.val('');
        return false;
            }
    /* ========================================================= */
    
    $(function() {
        //$("#startdate").datepicker();
        //$("#enddate").datepicker();
        //$("#startdateS").datepicker();
        //$("#enddateS").datepicker();
        $('#startdateS').scroller({
            preset: 'datetime', //date, time, select, 
            //invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] },
            theme: 'sense-ui', //jqm, ios, android-ics, android-ics light, android, sense-ui, default
            display: 'modal', //inline, 
            mode: 'clickpick', // clickpick, mixed, scroller
            dateOrder: 'mmD ddy',
            timeFormat: 'HH:ii',
            timeWheels: 'HHii'
        });
        $('#enddateS').scroller({
            preset: 'datetime', //date, time, select, 
            //invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] },
            theme: 'sense-ui', //jqm, ios, android-ics, android-ics light, android, sense-ui, default
            display: 'modal', //inline, 
            mode: 'clickpick', // clickpick, mixed, scroller
            dateOrder: 'mmD ddy',
            timeFormat: 'HH:ii',
            timeWheels: 'HHii'
        });    
        
    });
    /* ========================================================= */
    function addPropertyToTable()
    {
        var propertyID = document.getElementById('propertiesToAdd').value;
        
        if (propertyID != '-1')
            {
                if (($('tr#' + propertyID).length!=0) && (propertyID.indexOf('part')=='-1'))
                {
                    
                    $('#alerts').attr('title','Oops!');
                    $('#alerts').html('You have already added this property.');
                    $('#alerts').dialog({modal:true, zIndex: 1000000000, dialogClass: 'error', buttons: { "Ok": function() { $(this).dialog("close"); } }});
                    return false;
                }
                else
                {
                    if ($("#addXmlTable > tbody").length)
                    {
                        if (propertyID=='part')
                        {
                            if ($("tr[id^=part]").length)
                            {
                                var str;
                                var max=1;
                                /*IF ANY TR WITH PART ID ALREADY EXISTS IN THE TABLE...*/
                                $('#addXmlTable').find($("tr[id^=part]")).each(function(i, el) {
                                    
                                    str = el.id;
                                    str = str.substr(str.indexOf("_")+1,str.length);
                                    max = parseInt(str);
                                    if (parseInt(str)>parseInt(max))
                                    {
                                        max = parseInt(str);
                                    }
                                    
                                    //alert(str);
                                    
                                });
                                var newstr = parseInt(max)+1;
                                $("<tr class='rows' id='" + propertyID + "_" + newstr + "'><td class='columnsHidden'>" + propertyID + "_" + newstr + "</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "_" + newstr + "'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "_" + newstr + "</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "_" + newstr + "' value='' class='inputTxt' /></td><td class='columnsValue'><input type='text' id='txtSeparator_" + propertyID + "_" + newstr + "' value='' maxlength='1' class='xmlSeparator'/></td></tr>").appendTo("#addXmlTable tbody");
                            }
                            else
                            {
                                $("<tr class='rows' id='" + propertyID + "_1'><td class='columnsHidden'>" + propertyID + "_1</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "_1'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "_1</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "_1' value='' class='inputTxt' /></td><td class='columnsValue'><input type='text' id='txtSeparator_" + propertyID + "_1' value='' maxlength='1' class='xmlSeparator'/></td></tr>").appendTo("#addXmlTable tbody");
                            }
                        }
                        else
                        {
                            $("<tr class='rows' id='" + propertyID + "'><td class='columnsHidden'>" + propertyID + "</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "' value='' class='inputTxt' /></td><td>&nbsp;</td></tr>").appendTo("#addXmlTable tbody");
                        }
                    }
                    else
                    {
                        if (propertyID=='part')
                        {
                            if ($("tr[id^=part]").length)
                            {
                                var str;
                                var max=1;
                                /*IF ANY TR WITH PART ID ALREADY EXISTS IN THE TABLE...*/
                                $('#addXmlTable').find($("tr[id^=part]")).each(function(i, el) {
                                    
                                    str = el.id;
                                    str = str.substr(str.indexOf("_")+1,str.length);
                                    max = parseInt(str);
                                    if (parseInt(str)>parseInt(max))
                                    {
                                        max = parseInt(str);
                                    }
                                    
                                    //alert(str);
                                    
                                    
                                });
                                var newstr = parseInt(max)+1;
                                $("<tbody><tr class='rows' id='" + propertyID + "_" + newstr + "'><td class='columnsHidden'>" + propertyID + "_" + newstr + "</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "_" + newstr + "'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "_" + newstr + "</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "_" + newstr + "' value='' class='inputTxt' /></td><td class='columnsValue'><input type='text' id='txtSeparator_" + propertyID + "_" + newstr + "' value='' maxlength='1' class='xmlSeparator'/></td></tr></tbody>").appendTo("#addXmlTable");
                            }
                            else
                            {
                                //alert("<input type='text' id='txtSeparator_" + propertyID + "_1' value='' style='width:20px;'/></td>");
                                $("<tbody><tr class='rows' id='" + propertyID + "_1'><td class='columnsHidden'>" + propertyID + "_1</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "_1'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "_1</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "_1' value='' class='inputTxt' /></td><td class='columnsValue'><input type='text' id='txtSeparator_" + propertyID + "_1' value='' maxlength='1' class='xmlSeparator'/></td></tr></tbody>").appendTo("#addXmlTable");
                                
                            }
                        }
                        else
                        {
                            $("<tbody><tr class='rows' id='" + propertyID + "'><td class='columnsHidden'>" + propertyID + "</td><td class='columnsCheck'><input type='checkbox' name='chkProperties' id='chkProperty_" + propertyID + "'/></td><td class='columnsProperty'>" + $("#propertiesToAdd option[value='" + propertyID + "']").text() + "</td><td class='columnsValue'><input type='text' id='txtProperty_" + propertyID + "' value='' class='inputTxt' /></td><td>&nbsp;</td></tr></tbody>").appendTo("#addXmlTable");
                        }
                        
                    }
                }       
            }
            else
            {
                $('#alerts').attr('title','Oops!');
                $('#alerts').html('You must select property to add.');
                $('#alerts').dialog({modal:true, zIndex: 1000000000, dialogClass: 'error', buttons: { "Ok": function() { $(this).dialog("close"); } }});
                return false;
            }
            $('#addXmlTable').find($("tr[id^=port]")).each(function(i, el) {
                checkNumbering($('#txtProperty_port'));

            });
            
            drawOddEvenRows($('#addXmlTable'), $("tbody"), $("tr"));
    };
    /* ========================================================= */
    function deletePropertyFromTable()
    {
        var chks = document.getElementsByName('chkProperties');
        var propertyID;
        var somethingIsChecked=false;
        var chksToBeRemoved = new Array();
        for (i = 0; i < chks.length; i++)
        {
            //alert(chks.length);
            if (chks[i].checked)
            {
                //alert('mpika_' + i)
                //alert(chks[i].id + '_checked');
                somethingIsChecked = true;
                //message = message + frm.Music[i].value + "\n"
                propertyID = chks[i].id;
                propertyID = propertyID.substr(propertyID.indexOf("_")+1,propertyID.length);
                //alert($('tr#' + propertyID));
                //$('tr#' + propertyID).remove();
                chksToBeRemoved.push(propertyID);
                
            }
            else
            {
                if (somethingIsChecked==false)
                {
                    somethingIsChecked = false;
                }
                
            }
        }
        
        /* CHECK IF ANYTHING IS SELECTED */
        if (somethingIsChecked == false)
        {
            $('#alerts').attr('title','Oops!');
            $('#alerts').html('You have to select a property to delete.');
            $('#alerts').dialog({modal:true, zIndex: 1000000000, dialogClass: 'error', buttons: { "Ok": function() { $(this).dialog("close"); } }});
            return false;
        }
        else
        {
            for (i=0; i<chksToBeRemoved.length;i++)
            {
                $('tr#' + chksToBeRemoved[i]).remove();
            }
            $('#addXmlTable').find($("tr[id^=part]")).each(function(index) {
                $(this).attr('id','part_' + parseInt(index+1)); //TR's ID
                $(this).find('td:eq(0)').html('part_' + parseInt(index+1));
                $(this).find('td:eq(1)').find($('input')).attr('id','chkProperty_part_' + parseInt(index+1));
                $(this).find('td:eq(2)').html('Part_' + parseInt(index+1));
                $(this).find('td:eq(3)').find($('input')).attr('id','txtProperty_part_' + parseInt(index+1));
                $(this).find('td:eq(4)').find($('input')).attr('id','txtSeparator_part_' + parseInt(index+1));
            });
        }
        drawOddEvenRows($('#addXmlTable'), $("tbody"), $("tr"));
        
    }
    
    /* ========================================================= */
    
    $(function () {
        $("select").selectbox();
    });
    
    /* ========================================================= */
    
    function compareStartDate(enddate, startdate)
    {
        if (Date.parse(enddate.val())<=Date.parse(startdate.val()))
            {
                $('#alerts').attr('title','Oops!');
                $('#alerts').html('Your ending date must be greater than your starting date. Please try again.');
                $('#alerts').dialog({modal:true, dialogClass: 'error', zIndex: 1000000000, buttons: { "Ok": function() { $(this).dialog("close"); } }});
                e.preventDefault();
            }
    }
    
    /* ========================================================= */
    
    function drawOddEvenRows(tableobj, tbodyobj, trobj)
    {
        tableobj.find(tbodyobj).find(trobj).each(function(i, el) {
            if (i%2==0)
            {
                //$(this).addClass("even");
            }
            else
            {
                $(this).addClass("ui-state-active");
            }
        });
    }
    