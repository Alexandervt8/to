#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QRandomGenerator>
#include <algorithm>    // std::count_if
#include <QString>
#include <QVector>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_btnGenerar_clicked()
{
    numeros.clear();
    ui->txtNumeros->clear();

    // Generar 20 números aleatorios entre 1 y 50
    for (int i = 0; i < 20; ++i) {
        int n = QRandomGenerator::global()->bounded(1, 51);
        numeros.append(n);
        ui->txtNumeros->append(QString::number(n));
    }

    // Contar los impares menores de 20
    int contador = std::count_if(numeros.begin(), numeros.end(),
                                 [](int n) { return (n % 2 != 0) && (n < 20); });

    // Extraer los números impares menores de 20
    QVector<int> impares;
    std::copy_if(numeros.begin(), numeros.end(), std::back_inserter(impares),
                 [](int n) { return (n % 2 != 0) && (n < 20); });

    // Mostrar los impares en un label
    QString listaImpares;
    for (int n : impares) {
        listaImpares += QString::number(n) + " ";
    }

    ui->lblResultado->setText("Impares menores de 20: " + QString::number(contador));
    ui->lblImpares->setText("Números impares: " + (listaImpares.isEmpty() ? "Ninguno" : listaImpares));
}
