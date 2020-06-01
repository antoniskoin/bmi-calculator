import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class SearchData {
    
    private String query;
    private JTable dataTable;

    /** 
     * @param query Search query.
     * @param dataTable Activity Table
     */
    public SearchData(String query, JTable dataTable) {
        this.query = query;
        this.dataTable = dataTable;
    }

    /**
     * @param query Search query.
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return query Search query.
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param dataTable Activity Table
     */
    public void setDataTable(JTable dataTable) {
        this.dataTable = dataTable;
    }

    /**
     * @return dataTable Activity Table
     */
    public JTable getDataTable() {
        return dataTable;
    }
    
    /**
     * Gets as input the user search query and splits it
     * using comma as a delimiter.
     * @param text Text to process.
     * @return removes all spaces and return an array of words.
     */
    public static String[] getRow(String text) {
        String line = text.replaceAll(" ", ""); // REMOVE ALL SPACES IF USER USED SPACE
        line = line.replaceAll("\"", "");
        return line.split(",");
    }

    /**
     * The orSearch function searches the user's activty and it will show
     * results if any of the provided words exists in the table.
     * @param table Activity Table
     * @param query User's search query
     * @param model JTable's model
     */
    public static void orSearch(JTable table, String query, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> TRS = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(TRS);

        String[] tRow = getRow(query);
        List<RowFilter<Object, Object>> orFilters = new ArrayList<RowFilter<Object, Object>>(tRow.length);
        for (int i = 0; i < tRow.length; i++) {
            orFilters.add(RowFilter.regexFilter(tRow[i]));
        }
        RowFilter<Object, Object> orFilter = RowFilter.orFilter(orFilters);
        TRS.setRowFilter(orFilter);
    }

    /**
     * The andSearch function searches the user's activty and it will show
     * results if all of the provided words exist in the table.
     * @param table Activity Table
     * @param query User's search query
     * @param model JTable's model
     */
    public static void andSearch(JTable table, String query, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> TRS = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(TRS);
        String[] tRow = getRow(query);
        List<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>(tRow.length);
        for (int i = 0; i < tRow.length; i++) {
            andFilters.add(RowFilter.regexFilter(tRow[i]));
        }

        RowFilter<Object, Object> andFilter = RowFilter.andFilter(andFilters);
        TRS.setRowFilter(andFilter);
    }
    
    /**
     * The freeSearch function allows the user to type any text to
     * search his/her activity.
     * @param table Activity Table
     * @param query User's search query
     * @param model JTable's model
     */
    public static void freeSearch(JTable table, String query, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> TRS = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(TRS);
        TRS.setRowFilter(RowFilter.regexFilter(query));
    }

}
