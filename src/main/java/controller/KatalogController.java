package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import data.ProdukRepository;
import model.Produk;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.util.Optional;

public class KatalogController {

    @FXML
    private TextField namaField;
    @FXML
    private TextField hargaField;
    @FXML
    private ComboBox<String> fotoBox;
    @FXML
    private Label pesanLabel;
    @FXML
    private Label jumlahLabel;
    @FXML
    private TilePane katalogPane;
    private final ProdukRepository repository = new ProdukRepository();

    @FXML
    private void initialize() {
        fotoBox.getItems().add("roti.png");
        fotoBox.getItems().add("susu.png");
        fotoBox.getItems().add("kopi.png");
        fotoBox.setValue("roti.png");
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        ArrayList<Produk> daftar = repository.getAll();
        katalogPane.getChildren().clear();
        for (Produk produk : daftar) {
            VBox kartu = createProductCard(produk);
            katalogPane.getChildren().add(kartu);
        }
        jumlahLabel.setText("Jumlah produk: " + daftar.size());
    }

    private VBox createProductCard(Produk produk) {
        ImageView fotoView = new ImageView();
        fotoView.setFitWidth(170);
        fotoView.setFitHeight(110);
        fotoView.setPreserveRatio(true);
        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 120);
        bingkai.setMinSize(180, 120);
        bingkai.setMaxSize(180, 120);

        URL lokasi = getClass().getResource("/image/" + produk.getFoto());
        if (lokasi != null) {
            Image gambar = new Image(lokasi.toExternalForm(),
                    170, 110, true, true);
            if (!gambar.isError()) {
                fotoView.setImage(gambar);
                bingkai.getChildren().add(fotoView);
            } else {
                bingkai.getChildren().add(new Label("Gambar tidak terbaca"));
            }
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label namaLabel = new Label(produk.getNama());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-produk");
        Label hargaLabel = new Label("Rp" + produk.getHarga());
        Label idLabel = new Label("ID: " + produk.getId());
        idLabel.getStyleClass().add("muted");

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu-produk");
        kartu.getChildren().add(bingkai);
        kartu.getChildren().add(namaLabel);
        kartu.getChildren().add(hargaLabel);
        kartu.getChildren().add(idLabel);
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> editProduct(produk));
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteProduct(produk));
        HBox tombol = new HBox(10);
        tombol.getChildren().addAll(updateButton, deleteButton);
        kartu.getChildren().add(tombol);
        return kartu;
    }
    
    private void editProduct(Produk produk) {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Edit Produk");
    dialog.setHeaderText("Ubah informasi produk");

    ButtonType tombolSimpan = new ButtonType(
        "Simpan", ButtonType.OK.getButtonData()
    );

    dialog.getDialogPane().getButtonTypes().addAll(
        tombolSimpan, ButtonType.CANCEL
    );

    TextField namaEdit = new TextField(produk.getNama());
    TextField hargaEdit = new TextField(
        String.valueOf(produk.getHarga())
    );

    ComboBox<String> fotoEdit = new ComboBox<>();
    fotoEdit.getItems().addAll(
        "roti.png", "susu.png", "kopi.png"
    );
    fotoEdit.setValue(produk.getFoto());

    GridPane form = new GridPane();
    form.setHgap(10);
    form.setVgap(10);
    form.setPadding(new Insets(20));

    form.add(new Label("Nama Produk"), 0, 0);
    form.add(namaEdit, 1, 0);

    form.add(new Label("Harga"), 0, 1);
    form.add(hargaEdit, 1, 1);

    form.add(new Label("Foto"), 0, 2);
    form.add(fotoEdit, 1, 2);

    dialog.getDialogPane().setContent(form);

    Optional<ButtonType> hasil = dialog.showAndWait();

    if (hasil.isPresent() && hasil.get() == tombolSimpan) {

        try {
            String nama = namaEdit.getText();
            long harga = Long.parseLong(
                hargaEdit.getText().trim()
            );
            String foto = fotoEdit.getValue();

            Produk produkBaru = new Produk(
                produk.getId(), nama, harga, foto
            );

            repository.update(produkBaru);

            showData();
            pesanLabel.setText("Produk berhasil diperbarui.");

        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus berupa angka.");
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
        } catch (SQLException e) {
            pesanLabel.setText("Gagal memperbarui produk.");
            e.printStackTrace();
        }
    }
}
    
    private void deleteProduct(Produk produk) {

    Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);

    konfirmasi.setTitle("Hapus Produk");
    konfirmasi.setHeaderText("Konfirmasi penghapusan");
    konfirmasi.setContentText(
        "Apakah kamu yakin ingin menghapus produk "
        + produk.getNama() + "?"
    );

    Optional<ButtonType> hasil = konfirmasi.showAndWait();

    if (hasil.isPresent()
            && hasil.get() == ButtonType.OK) {

        try {
            repository.delete(produk.getId());

            showData();
            pesanLabel.setText("Produk berhasil dihapus.");

        } catch (SQLException e) {
            pesanLabel.setText("Gagal menghapus produk.");
            e.printStackTrace();
        }
    }
}

    @FXML
    private void saveProduct() {
        Produk produk;
        try {
            String nama = namaField.getText();
            long harga = Long.parseLong(hargaField.getText().trim());
            String foto = fotoBox.getValue();
            produk = new Produk(nama, harga, foto);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus angka bulat, contoh 12000.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }
        try {
            repository.add(produk);
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan. Lihat Output.");
            e.printStackTrace();
            return;
        }
        namaField.clear();
        hargaField.clear();
        try {
            showData();
            pesanLabel.setText("Produk berhasil disimpan.");
        } catch (SQLException e) {
            pesanLabel.setText("Data tersimpan, tetapi katalog gagal dimuat ulang.");
            e.printStackTrace();
        }
    }
}
