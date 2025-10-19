#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
    , ventanaTask(nullptr)
{
    ui->setupUi(this);
    connect(ui->addTaskButton, &QPushButton::clicked, this, &MainWindow::abrirTask);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::abrirTask()
{
    if (!ventanaTask) {
        ventanaTask = new Task(this);
        connect(ventanaTask, &Task::textoCambiado, this, &MainWindow::actualizarTexto);
    }
    ventanaTask->show();
}

void MainWindow::actualizarTexto(QString textoRecibido)
{
    ui->statusLabel->setText(textoRecibido);
}
