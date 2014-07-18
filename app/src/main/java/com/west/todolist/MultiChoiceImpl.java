package com.west.todolist;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usr1 on 7/18/14.
 */
public class MultiChoiceImpl implements AbsListView.MultiChoiceModeListener {
    private AbsListView listView;

    public MultiChoiceImpl(AbsListView listView) {
        this.listView = listView;
    }

    @Override
    //Метод вызывается при любом изменения состояния выделения рядов
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

        int selectedCount = listView.getCheckedItemCount();
        //Добавим количество выделенных рядов в Context Action Bar
        setSubtitle(actionMode, selectedCount);
    }

    @Override
    //Здесь надуваем наше меню из xml
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.contextual_list_view, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        Toast.makeText(listView.getContext(), "Action - " + menuItem.getTitle() + " ; Selected items: " + getSelectedFiles(), Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

    private void setSubtitle(ActionMode mode, int selectedCount) {
        switch (selectedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            default:
                mode.setTitle(String.valueOf(selectedCount));
                break;
        }
    }

    private List<String> getSelectedFiles() {
        List<String> selectedFiles = new ArrayList<String>();

        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            if (sparseBooleanArray.valueAt(i)) {
                Integer selectedItem = (Integer) listView.getItemAtPosition(sparseBooleanArray.keyAt(i));
                selectedFiles.add("#" + Integer.toHexString(selectedItem).replaceFirst("ff", ""));
            }
        }
        return selectedFiles;
    }
}
