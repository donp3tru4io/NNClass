/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nnclass;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author donp3tru4io
 */
public class NeuralNetwork {
    
    private Neuron[] input;
    private ArrayList<Neuron[]> hidden;
    private Neuron output;
    
    
    
    public NeuralNetwork(int inputNeuron,int hiddenLayer,int[] hiddenNeuron)
    {
        input = new Neuron[inputNeuron];
        hidden = new ArrayList<Neuron[]>();
        for(int i = 0;i<hiddenLayer;i++)
        {
            hidden.add(new Neuron[hiddenNeuron[i]]);
        }
        
        initNeurons();
        output = new Neuron();
        
        chainInputHidden();
        chainHidden();
        chainHiddenOutput();
        
    }
    
    private void initNeurons()
    {
        for(int i = 0; i < input.length;i++)
        {
            input[i]=new Neuron();
        }
        for(Neuron[] n : hidden)
        {
            for(int i = 0; i < n.length;i++)
            {
                n[i]=new Neuron();
            }  
        }
    }
    
    
    private void chainInputHidden()
    {
        for(int i = 0; i< input.length;i++)
        {
            for(int j = 0; j < hidden.get(0).length;j++)
            {
                Chain chain = new Chain(input[i],hidden.get(0)[j]);
                chain.setWeight(ThreadLocalRandom.current().nextDouble(0.5));
                input[i].addOutputChain(chain);
                hidden.get(0)[j].addInputChain(chain);
            }
        }
    }
    
    private void chainHidden()
    {
        for(int k = 0;k<hidden.size()-1;k++)
        {
            for(int i = 0; i< hidden.get(k).length;i++)
            {
                for(int j = 0; j < hidden.get(k+1).length;j++)
                {
                    Chain chain = new Chain(hidden.get(k)[i],hidden.get(k+1)[j]);
                    chain.setWeight(ThreadLocalRandom.current().nextDouble(0.5));
                    hidden.get(k)[i].addOutputChain(chain);
                    hidden.get(k+1)[j].addInputChain(chain);
                }
            }
        }
    }
    
    private void chainHiddenOutput()
    {
        int k = hidden.size()-1;
        for(int i = 0; i< hidden.get(k).length;i++)
        {
            Chain chain = new Chain(hidden.get(k)[i],output);
            chain.setWeight(ThreadLocalRandom.current().nextDouble(0.5));
            hidden.get(k)[i].addOutputChain(chain);
            output.addInputChain(chain);
        }
    }
        
    public double predict(double[] data)
    {
        for(int i = 0;i<input.length;i++)
        {
            input[i].setValue(data[i]);
        }
        
        for(int i = 0;i<hidden.size();i++)
        {
            for(int j = 0; j < hidden.get(i).length;j++)
            {
                hidden.get(i)[j].calcValue();
            }
        }
        
        return output.calcValue();
    }
    
    public void backpropagation(double prediction,double expectation,double learningRate)
    {
        double error = prediction - expectation;
        
        output.calcdW(error);
        
        for(Chain chain : output.getInputChain())
        {
            chain.changeWeight(learningRate);
        }
        
        for(int i = hidden.size()-1; i > -1;i--)
        {
            for(Neuron neuron : hidden.get(i))
            {
                double _error=0;
                for(Chain chain: neuron.getOutputChain())
                {
                    _error+= chain.getB().getdW()*chain.getWeight();
                }
                neuron.calcdW(_error);
                for(Chain chain : neuron.getInputChain())
                {
                    chain.changeWeight(learningRate);
                }
            }
        }
        
    }
    
    public void train(double[][] data,double[] expectation ,int ages, double learningRate)
    {
        for(int i = 0; i< ages;i++)
        {
            double mse = 0;
            
            for(int j = 0; j< expectation.length;j++)
            {
                double prediction = predict(data[j]);
                mse+= Math.pow(prediction - expectation[j],2);
                backpropagation(prediction,expectation[j],learningRate);
            }
            
            String progress = i/(double)ages +"";
            System.out.flush();
            System.out.println("MSE : "+Math.sqrt(mse/expectation.length)+"Progress : "+progress+" %");
        }
        
        for(int i = 0;i<expectation.length;i++)
        {
            System.out.println("prediction = "+predict(data[i])+" expectation = " + expectation[i]);
        }
    }
    
}
