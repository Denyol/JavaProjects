package me.denyol.chatapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.util.Callback;
import me.denyol.chatapp.Main;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainAppController {

	@FXML private ListView<String> listView;
	@FXML private TextArea textArea;

	private Main main;

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

							setPrefWidth(0);
							setMaxWidth(Control.USE_PREF_SIZE);

							setText(s);
						}
					}
				};
			}
		});
	}

	public void init(Main main) {
		this.main = main;

		this.main.getServerThread().getMessageOutQueue().offer(main.getName() + " joined the room!");
	}

	public ListView<String> getListView() {
		return listView;
	}

	public void addText(String text) {
		this.getListView().getItems().add(text);
	}

	@FXML
	private void onSendButtonAction(ActionEvent event) {
		StringBuilder stringBuilder = new StringBuilder();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();

		stringBuilder.append("(");
		stringBuilder.append(dtf.format(localDate));
		stringBuilder.append(") ");

		stringBuilder.append(main.getName());
		stringBuilder.append(": ");
		stringBuilder.append(this.textArea.getText());

		boolean result = this.main.getServerThread().getMessageOutQueue().offer(stringBuilder.toString());

		if(result = false)
			listView.getItems().add("Could not add item!");
		else {
			this.textArea.setText("");
		}
	}

	@FXML
	public void handleLeaveButtonAction(ActionEvent actionEvent) throws IOException {
		main.getServerThread().getMessageOutQueue().offer(main.getName() + " is leaving the room! o/");
		this.main.getServerThread().stopRunning();
		this.main.getClientThread().stopRunning();
		this.main.startSignOnScene();
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
