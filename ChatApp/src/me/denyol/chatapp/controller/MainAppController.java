package me.denyol.chatapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.util.Callback;

public class MainAppController {

	@FXML private ListView<String> listView;
	@FXML private Button sendButton;
	@FXML private TextArea textArea;

	@FXML
	public void initialize() {
		listView.setSelectionModel(new NullStringSelectionModel());
		listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> stringListView) {
				return new ListCell<String>() {

					@Override
					protected void updateItem(String s, boolean empty) {
						super.updateItem(s, empty);

						if(empty || s == null || s.isEmpty()) {
							setText(null);
							setGraphic(null);
							setBackground(new Background(new BackgroundFill(null, null, null)));
						} else {
							setWrapText(true);

							//prefWidthProperty().bind(getListView().widthProperty().subtract(2));
							setPrefWidth(0);
							setMaxWidth(Control.USE_PREF_SIZE);

							setText(s);
						}
					}
				};
			}
		});

		listView.setItems(FXCollections.observableArrayList("(6:07 PM) Daniel: Tgis is my measssashshshshhshshhsgeshshshs"));
		listView.getItems().add("(6:07 PM) Rachel: Tgis is my measssashshshshhshshhsgeshshshs");
	}

	public ListView<String> getListView() {
		return listView;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void addText(String text) {
		this.getListView().getItems().add(text);
	}
}

class NullStringSelectionModel extends MultipleSelectionModel<String> {

	@Override
	public ObservableList<Integer> getSelectedIndices() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public ObservableList<String> getSelectedItems() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public void selectIndices(int i, int... ints) {
	}

	@Override
	public void selectAll() {
	}

	@Override
	public void clearAndSelect(int i) {
	}

	@Override
	public void select(int i) {

	}

	@Override
	public void select(String s) {

	}

	@Override
	public void clearSelection(int i) {

	}

	@Override
	public void clearSelection() {

	}

	@Override
	public boolean isSelected(int i) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void selectPrevious() {

	}

	@Override
	public void selectNext() {

	}

	@Override
	public void selectFirst() {

	}

	@Override
	public void selectLast() {

	}
}
