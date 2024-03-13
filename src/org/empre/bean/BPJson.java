package org.empre.bean;

import java.util.List;

public class BPJson {
    private Options options;
    //private Map<String, String> options;
   // private String bpname;
    
    List<DataItems> data;

    public BPJson(Options options, List<DataItems> data) {
        this.options = options;
        this.data = data;
    }

   
    public static class Options{
            public String bpname;
        public Options(String bpname) {
            this.bpname = bpname;
        }
    }


    public static class DataItems{
            String uconPOFBLTXT120;
            String ugenVendorBP;
            String upoContractTypePD;
            String uconDynamicsTXT120;
            String uconContractNumberTXT120;
            String SupplierOrderNumberTxt16;
            String title;
            String ugenActivityDPK;
            String upoContractDescMLT4000;
            Double amount;
            String LineId;
            String creator_id;
            String status;
            List<Bp_lineitems> _bp_lineitems;

        public DataItems(String uconPOFBLTXT120,
            String ugenVendorBP,
            String upoContractTypePD,
            String uconDynamicsTXT120,
            String uconContractNumberTXT120,
            String SupplierOrderNumberTxt16,
            String title,
            String ugenActivityDPK,
            String upoContractDescMLT4000,
            Double amount,
            String LineId,
            String creator_id,
            String status,
            List<Bp_lineitems> _bp_lineitems) {

           this.uconPOFBLTXT120 = uconPOFBLTXT120;
           this.ugenVendorBP = ugenVendorBP;
           this.upoContractTypePD = upoContractTypePD;
           this.uconDynamicsTXT120 = uconDynamicsTXT120;
           this.uconContractNumberTXT120 = uconContractNumberTXT120;
           this.SupplierOrderNumberTxt16 = SupplierOrderNumberTxt16;
           this.title = title;
           this.ugenActivityDPK = ugenActivityDPK;
           this.upoContractDescMLT4000 = upoContractDescMLT4000;
           this.amount = amount;
           this.LineId = LineId;
           this.creator_id = creator_id;
           this.status = status;
           this._bp_lineitems = _bp_lineitems;
           
        }

    }

    public static class Bp_lineitems {
        Double uuu_quantity;
        String ugenUnitofMeasurePD;
        Double amount;
        String description;
        String uuu_cost_li_type;
        String ugenActivityDPK;
        String short_desc;
        List<Cost_allocation> _cost_allocation;

        public Bp_lineitems(Double uuu_quantity,
                            String ugenUnitofMeasurePD,
                            Double amount,
                            String description,
                            String uuu_cost_li_type,
                            String ugenActivityDPK,
                            String short_desc,
                            List<Cost_allocation> _cost_allocation) {
            this.ugenUnitofMeasurePD = ugenUnitofMeasurePD;
            this.amount = amount;
            this.description = description;
            this.uuu_cost_li_type = uuu_cost_li_type;
            this.ugenActivityDPK = ugenActivityDPK;
            this.short_desc = short_desc;
            this._cost_allocation = _cost_allocation;
        }

    }
    
    
    public static class Cost_allocation {
        Double uuu_quantity;
        Double amount;
       // String uuu_cost_li_type;
        String bItemID;
        String short_desc;


        public Cost_allocation(Double uuu_quantity, Double amount, String bItemID, String short_desc) {
            this.uuu_quantity = uuu_quantity;
            this.amount = amount;
            this.bItemID = bItemID;
            this.short_desc = short_desc;
        }

      
    }
}
