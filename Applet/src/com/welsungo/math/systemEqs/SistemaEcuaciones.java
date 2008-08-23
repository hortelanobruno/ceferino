package com.welsungo.math.systemEqs;

import com.welsungo.math.Messages;
import com.welsungo.math.systemEqs.exceptions.NoUniqueSolutionException;

/*
 * Created on 13-feb-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author felipe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SistemaEcuaciones {
	private double mCoef[][]; 
	private int mNumEcs;
	
	/**
	 * @return
	 */
	public int getNumEcs() {
		return mNumEcs;
	}

	/**
	 * @param i
	 */
	public void setNumEcs(int i) {
		mNumEcs = i;
	}
	
	/**
	 * @return
	 */
	public double[][] getCoef() {
		return mCoef;
	}

	/**
	 * @param ds
	 */
	public void setCoef(double[][] ds) {
		mCoef = ds;
	}
	
	public double[] getSolucion() { 
		double x;
		double y;
		int j;
		int i;
		int k;

		for(j=0; j<mNumEcs; j++) {
			//Encontramos la primera ecuacion con un coeficiente no cero 
			//en la columna (ecuación) que estemos mirando (j)
			for(i=j; i<mNumEcs; i++){
				if(mCoef[i][j] != 0D) {
					break;
				}
				throw new NoUniqueSolutionException(Messages.getString("SistemaEcuaciones.SolucionNoUnica"));
			}
			//(+) Movemos esa ecuación a la primera fila
			for(k=0; k<mNumEcs+1; k++){
				x = mCoef[j][k];
				mCoef[j][k] = mCoef[i][k];
				mCoef[i][k] = x;
			}
			
			//(+) Obtenemos un coeficiente unidad en la primera columna no cero
			y = 1/mCoef[j][j];
			for(k=0; k<mNumEcs+1; k++){
				mCoef[j][k]=y*mCoef[j][k];
			}

			for(i=0; i<mNumEcs; i++){
				y = -mCoef[i][j];
				for(k=0; k<mNumEcs+1; k++){
					if(i==j) break;
					mCoef[i][k]=mCoef[i][k]+y*mCoef[j][k];
				}
			}
		}
		double dRet[] = new double[mNumEcs];
		for(i=0; i<mNumEcs; i++){
			double dRes1 = mCoef[i][mNumEcs]*1000+0.5;
			int iRes = (int)dRes1;
			double dRes2 = iRes/1000D;
			dRet[i] = dRes2;
		}
		for(i=0; i<mNumEcs; i++){
			System.out.println("x("+i+")= "+dRet[i]);
		}
		return dRet;
	}
}
