import java.util.Random;
//package com.quicklyjava;


import java.io.File;
import java.io.IOException;
import java.util.Date;
 
import jxl.*;
import jxl.write.*;
import jxl.write.Boolean;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
public class FibonacciHeap_Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub//
		testCase1();
		
//		try {
//			writeExcel();
//		} catch (WriteException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	private static void testCase1(){
		FibonacciHeap test_heap = new FibonacciHeap();
		FibonacciHeap.HeapNode [] nodes_array = new FibonacciHeap.HeapNode [6];
		for(int i = 0; i < 6 ; i++){ 
			nodes_array[i] = test_heap.insert(i+1);
			System.out.println(String.format("Adding node with key %d",i+1));
		}
		if(test_heap.size() != 6){
			System.out.println("Test case 1 - Error 1 : problem with tree size in insertion");
		}
		test_heap.deleteMin();
		if(test_heap.size() != 5){
			System.out.println("Test case 1 - Error 2 : problem with tree size after deleteMin");
		}
		if(test_heap.findMin().key != 2){
			System.out.println("Test case 1 - Error 3 : problem with deleteMin");
		}
		if(test_heap.findMin().nextNode.key != 6){
			System.out.println(String.format(
					"Test case 1 - Error 4 : problem with deleteMin. Sibiling should be 6 but it is %s",
					test_heap.findMin().nextNode.key));
		}
		if(test_heap.findMin().child.key != 4){
			System.out.println(String.format(
					"Test case 1 - Error 5 : problem with deleteMin. Left child should be 4 but it is %s",
					test_heap.findMin().child.key));
		}
		if(test_heap.findMin().child.child.key != 5){
			System.out.println(String.format(
					"Test case 1 - Error 6 : problem with deleteMin. Left child child should be 5 but it is %s",
					test_heap.findMin().child.child.key));
		}
		if(test_heap.findMin().child.nextNode.key != 3){
			System.out.println(String.format(
					"Test case 1 - Error 7 : problem with deleteMin. Left child brother should be 3 but it is %s",
					test_heap.findMin().child.nextNode.key));
		}

		if(test_heap.totalCuts() != 0){
			System.out.println(String.format(
					"Test case 1 - Error 9 : problem with cuts counter. total cuts should be 0 but it is %s",
					test_heap.totalCuts()));
		}
		if(test_heap.totalLinks() != 3){
			System.out.println(String.format(
					"Test case 1 - Error 10 : problem with links counter. total cuts should be 3 but it is %s",
					test_heap.totalLinks()));
		}
		int [] countersRep = test_heap.countersRep();
		if(countersRep[0]!= 1 ||countersRep[1]!= 0 || countersRep[2] != 1){
			System.out.println(String.format(
					"Test case 1 - Error 11 : problem with countersRep. array is: %s /n instead of [1,0,1]",
					test_heap.countersRep().toString()));
		}
		if(test_heap.potential() != 2){
			System.out.println(String.format(
					"Test case 1 - Error 12 : problem with potential function. Potential should"
					+ "be 2 but it is %s", test_heap.potential()));
		}
		FibonacciHeap test_heap2 = new FibonacciHeap();
		test_heap2.insert(1);
		//melding... 
		test_heap2.meld(test_heap);
		if(test_heap2.findMin().key != 1){
			System.out.println(String.format(
					"Test case 1 - Error 13 : problem with meld. min key should be 1 but it is %s",
					test_heap2.findMin().key));
		}
		if(test_heap2.findMin().nextNode.key != 2){
			System.out.println(String.format(
					"Test case 1 - Error 14 : problem with meld. Sibiling should be 2 but it is %s",
					test_heap2.findMin().nextNode.key));
		}	
		test_heap.delete(nodes_array[2]);
		test_heap.delete(nodes_array[4]);
		System.out.println("Test case 1 - Done! ");
		
	}
	
	
	
	private static void writeExcel() throws WriteException, IOException {
		WritableWorkbook workBook = null;
		try {
			//initialize workbook
			for(int j = 1; j<3; j++){
				String filePath = String.format("src\\output%s.xls", j);
				workBook = Workbook.createWorkbook(new File(filePath));
				//create sheet with name as Employee and index 0
				WritableSheet sheet = workBook.createSheet("FibonacciHeap measures", 0);
				// create font style for header cells
				WritableFont headerCellFont = new WritableFont(WritableFont.ARIAL, 14,
						WritableFont.BOLD, true);
				//create format for header cells
				WritableCellFormat headerCellFormat = new WritableCellFormat(
						headerCellFont);
				// create header cells
				Label headerCell1 = new Label(0, 0, "m", headerCellFormat);
				Label headerCell2 = new Label(1, 0, "Run-Time(ms)", headerCellFormat);
				Label headerCell3 = new Label(2, 0, "totalLinks", headerCellFormat);
				Label headerCell4 = new Label(3, 0, "totalCuts", headerCellFormat);
				Label headerCell5 = new Label(4, 0, "Potential", headerCellFormat);
				// add header cells to sheet
				sheet.addCell(headerCell1);
				sheet.addCell(headerCell2);
				sheet.addCell(headerCell3);
				sheet.addCell(headerCell4);
				sheet.addCell(headerCell5);
				
				long startTime = System.currentTimeMillis();
				for(int i=1; i<4;i++){
					FibonacciHeap.TotalCuts = 0;
					FibonacciHeap.TotalLinks = 0;
					FibonacciHeap test_heap = new FibonacciHeap();
					// insertion part
					heapInsertor(test_heap, i*1000);
					if (j == 2){ // second measurements
						heapDeletor(test_heap, i*1000);
					}
					int totalLinks = test_heap.totalLinks();
					int totalCuts = test_heap.totalCuts();
					int potential = test_heap.potential();
					long stopTime = System.currentTimeMillis();
					long runTime = stopTime - startTime;
					Label m = new Label(0, i, String.format("%s",i*1000));
					Label xl_runTime = new Label(1, i,String.format("%s", runTime));
					Label xl_totalLinks = new Label(2, i,String.format("%s",totalLinks));
					Label xl_totalCuts = new Label(3, i,String.format("%s",totalCuts));
					Label xl_potential = new Label(4, i, String.format("%s",potential));

					sheet.addCell(m);
					sheet.addCell(xl_runTime);
					sheet.addCell(xl_totalLinks);
					sheet.addCell(xl_totalCuts);
					sheet.addCell(xl_potential);
				}
				//write workbook
				workBook.write();
				workBook.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			//close workbook
			
		}
	}
	
	public static void heapInsertor(FibonacciHeap heap, int amount){
		for(int i = amount; i>0; i--){
			heap.insert(i);
		}
	}
	
	public static void heapDeletor(FibonacciHeap heap, int amount){
		for(int i = 0; i<amount/2; i++){
			heap.deleteMin();
		}
	}
}
