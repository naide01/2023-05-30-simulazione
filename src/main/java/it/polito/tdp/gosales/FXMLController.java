package it.polito.tdp.gosales;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private ComboBox<Retailers> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	Retailers retailer = this.cmbRivenditore.getValue();
    	if (retailer == null) {
    		this.txtResult.setText("Inserire un rivenditore dal menù a tendina! \n");
    		return;
    	}
    	this.txtResult.appendText("La componente connessa di " + retailer.getName() + "è: " + this.model.analizzaComopnente(retailer).getConnessi().size() 
    			+ " con peso= " + this.model.analizzaComopnente(retailer).getPeso() + "\n");

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String nazione = this.cmbNazione.getValue();
    	Integer anno = this.cmbAnno.getValue();
    	if (anno == null || nazione == null) {
    		this.txtResult.setText("Inserire un anno e una nazione dal menù a tendina! \n");
    		return;
    	}
    	Integer n = 0;
    	try {
			n = Integer.parseInt(this.txtNProdotti.getText());
		} catch (NumberFormatException e) {
			this.txtResult.setText("Inserire un numero! \n");
			return;
		}
    	if (n<0) {
    		this.txtResult.setText("Inserire un valore numerico > 0! \n");
    		return;
    	}
    	this.txtResult.setText("Inserire un valore numerico! \n");
    	
    	
    	this.model.creaGrafo(nazione, anno, n);
    	this.txtResult.setText("Grafo creato con: " + this.model.numV() + " vertici e " + this.model.numA() + " archi! \n");
    	

    	for (Retailers r: this.model.vertxSet()) {
    		this.txtVertici.appendText(r + "\n");
    	}
    	
    	for (Arco a : this.model.archi(nazione, anno, n)) {
    		this.txtArchi.appendText(a + "\n");
    	}
    	this.cmbRivenditore.setDisable(false);
    	this.btnAnalizzaComponente.setDisable(false);
    	this.cmbRivenditore.getItems().setAll(this.model.getRetailers());
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbAnno.getItems().setAll(this.model.getAnno());
    	this.cmbNazione.getItems().setAll(this.model.getCountry());
    }

}