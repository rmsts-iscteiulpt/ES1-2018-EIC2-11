package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpWindow {

	private Stage pop_up_stage;
	private Scene pop_up_scene;
	private BorderPane pop_up_root_pane;
	private BorderPane pop_up_window_root_pane;
	
	private PopUpType type;

	private static final int POP_UP_SHADOW_GAP = 10;
	private static final int POP_UP_ROOT_PANE_WIDTH = 400;
	private static final int POP_UP_ROOT_PANE_HEIGHT = 240;
	private static final int POP_UP_WINDOW_TOP_BAR_HEIGHT = 20;

	private double xOffset, yOffset;

	private boolean confirmation;

	public PopUpWindow(Stage main_stage, PopUpType type, String text) {
		this.type = type;
		configurePopUpStage(main_stage);
		createPopUpRootPane();
		buildPopUpWindowRootPane();
		buildPopUpContent(text);
		buildPopUpScene();
		startPopUpStage();
	}

	private void configurePopUpStage(Stage main_stage) {
		pop_up_stage = new Stage();
		pop_up_stage.initModality(Modality.APPLICATION_MODAL);
		pop_up_stage.initOwner(main_stage);
		pop_up_stage.initStyle(StageStyle.TRANSPARENT);
	}

	private void createPopUpRootPane() {
		BorderPane pop_up_root_pane = new BorderPane();
		this.pop_up_root_pane = pop_up_root_pane;
		pop_up_root_pane.setPrefSize(POP_UP_ROOT_PANE_WIDTH + POP_UP_SHADOW_GAP,
				POP_UP_ROOT_PANE_HEIGHT + POP_UP_SHADOW_GAP);
		pop_up_root_pane.setMaxSize(POP_UP_ROOT_PANE_WIDTH + POP_UP_SHADOW_GAP,
				POP_UP_ROOT_PANE_HEIGHT + POP_UP_SHADOW_GAP);
		pop_up_root_pane.setId("pop_up_root_pane");
		HBox top_gap_pane = new HBox();
		top_gap_pane.setPrefSize(POP_UP_ROOT_PANE_WIDTH + POP_UP_SHADOW_GAP, POP_UP_SHADOW_GAP);
		top_gap_pane.setId("pop_up_shadow_gap");
		HBox bottom_gap_pane = new HBox();
		bottom_gap_pane.setPrefSize(POP_UP_ROOT_PANE_WIDTH + POP_UP_SHADOW_GAP, POP_UP_SHADOW_GAP);
		bottom_gap_pane.setId("pop_up_shadow_gap");
		VBox left_gap_pane = new VBox();
		left_gap_pane.setPrefSize(POP_UP_SHADOW_GAP, POP_UP_ROOT_PANE_HEIGHT + POP_UP_SHADOW_GAP);
		left_gap_pane.setId("pop_up_shadow_gap");
		VBox right_gap_pane = new VBox();
		right_gap_pane.setPrefSize(POP_UP_SHADOW_GAP, POP_UP_ROOT_PANE_HEIGHT + POP_UP_SHADOW_GAP);
		right_gap_pane.setId("pop_up_shadow_gap");
		pop_up_root_pane.setTop(top_gap_pane);
		pop_up_root_pane.setBottom(bottom_gap_pane);
		pop_up_root_pane.setLeft(left_gap_pane);
		pop_up_root_pane.setRight(right_gap_pane);
		pop_up_root_pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = pop_up_stage.getX() - event.getScreenX();
				yOffset = pop_up_stage.getY() - event.getScreenY();
			}
		});
		pop_up_root_pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pop_up_stage.setX(event.getScreenX() + xOffset);
				pop_up_stage.setY(event.getScreenY() + yOffset);
			}
		});
	}

	private void buildPopUpScene() {
		pop_up_scene = new Scene(pop_up_root_pane, POP_UP_ROOT_PANE_WIDTH, POP_UP_ROOT_PANE_HEIGHT);
		pop_up_scene.getStylesheets().add("/resources/css/pop_up_window.css");
		pop_up_scene.setFill(null);
	}

	private void buildPopUpWindowRootPane() {
		pop_up_window_root_pane = new BorderPane();
		pop_up_window_root_pane.setId("pop_up_window_root_pane");
		pop_up_window_root_pane.setPrefSize(POP_UP_ROOT_PANE_WIDTH, POP_UP_ROOT_PANE_HEIGHT);
		pop_up_window_root_pane.setMaxSize(POP_UP_ROOT_PANE_WIDTH, POP_UP_ROOT_PANE_HEIGHT);
		buildPopUpWindowTopBar();
		pop_up_root_pane.setCenter(pop_up_window_root_pane);
	}

	private void buildPopUpWindowTopBar() {
		VBox pop_up_window_top_bar = new VBox();
		if(type.equals(PopUpType.SUCCESSFULLY)) {
			pop_up_window_top_bar.setId("pop_up_window_top_bar_successfully");
		}else {
			pop_up_window_top_bar.setId("pop_up_window_top_bar");
		}
		pop_up_window_top_bar.setPrefSize(POP_UP_ROOT_PANE_WIDTH, POP_UP_WINDOW_TOP_BAR_HEIGHT);
		pop_up_window_top_bar.setMaxSize(POP_UP_ROOT_PANE_WIDTH, POP_UP_WINDOW_TOP_BAR_HEIGHT);
		HBox pop_up_window_top_bar_buttons_container = new HBox();
		pop_up_window_top_bar_buttons_container.setId("pop_up_window_top_bar_buttons_container");
		Button close_button = new Button();
		close_button.setId("pop_up_window_top_bar_close_button");
		close_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				confirmation = false;
				pop_up_stage.close();
			}
		});
		pop_up_window_top_bar_buttons_container.getChildren().addAll(close_button);
		pop_up_window_top_bar.getChildren().addAll(pop_up_window_top_bar_buttons_container);
		pop_up_window_root_pane.setTop(pop_up_window_top_bar);
	}

	private void buildPopUpContent(String text) {
		VBox pop_up_container = new VBox();
		pop_up_container.setId("pop_up_container");
		HBox pop_up_buttons_container = new HBox();
		pop_up_buttons_container.setId("pop_up_buttons_container");
		if (type.equals(PopUpType.WARNING)) {
			Label warning_label = new Label();
			warning_label.setId("warning_label");
			pop_up_container.getChildren().add(warning_label);
			pop_up_buttons_container.getChildren().addAll(buildOkButton());

		} else if (type.equals(PopUpType.CONFIRMATION)) {
			Label confirmation_label = new Label();
			confirmation_label.setId("confirmation_label");
			pop_up_container.getChildren().add(confirmation_label);
			pop_up_buttons_container.getChildren().addAll(buildOkButton(), buildCancelButton());
		} else if (type.equals(PopUpType.SUCCESSFULLY)) {
			Label successfully_label = new Label();
			successfully_label.setId("successfully_label");
			pop_up_container.getChildren().add(successfully_label);
			pop_up_buttons_container.getChildren().addAll(buildOkButton());
		}
		pop_up_container.getChildren().addAll(new Text(text), pop_up_buttons_container);
		pop_up_window_root_pane.setCenter(pop_up_container);
	}

	private Button buildOkButton() {
		Button ok_button = new Button();
		ok_button.setId("ok_button");
		ok_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				confirmation = true;
				pop_up_stage.close();
			}
		});
		return ok_button;
	}

	private Button buildCancelButton() {
		Button cancel_button = new Button();
		cancel_button.setId("cancel_button");
		cancel_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				confirmation = false;
				pop_up_stage.close();
			}
		});
		return cancel_button;
	}

	public boolean getConfirmation() {
		return confirmation;
	}

	private void startPopUpStage() {
		pop_up_stage.setScene(pop_up_scene);
		pop_up_stage.showAndWait();
	}
}
