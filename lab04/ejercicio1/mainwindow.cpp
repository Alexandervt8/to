#include "mainwindow.h"
#include "./ui_mainwindow.h"

#include <QListWidget>
#include <QListWidgetItem>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    prepararMapaColores();
    poblarLista();

    // Conectar el signal de clic de la lista al slot
    connect(ui->listColores, &QListWidget::itemClicked,
            this, &MainWindow::onColorClicked);

    ui->lblSeleccion->setText("(sin selección)");
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::prepararMapaColores()
{
    mapaColores.insert("Rojo",     Qt::red);
    mapaColores.insert("Verde",    Qt::green);
    mapaColores.insert("Azul",     Qt::blue);
    mapaColores.insert("Amarillo", Qt::yellow);
    mapaColores.insert("Cian",     Qt::cyan);
    mapaColores.insert("Magenta",  Qt::magenta);
    mapaColores.insert("Negro",    Qt::black);
    mapaColores.insert("Blanco",   Qt::white);
    mapaColores.insert("Gris",     Qt::gray);
    mapaColores.insert("Naranja",  QColor("orange"));
    mapaColores.insert("Morado",   QColor(128, 0, 128));
}

void MainWindow::poblarLista()
{
    ui->listColores->clear();
    for (const QString &nombre : mapaColores.keys())
        ui->listColores->addItem(nombre);
}

void MainWindow::onColorClicked(QListWidgetItem *item)
{
    if (!item) return;
    mostrarColor(item->text());
}

void MainWindow::mostrarColor(const QString &nombre)
{
    ui->lblSeleccion->setText(nombre);

    const QColor c = mapaColores.value(nombre, Qt::black);
    const QString colorCss =
        QString("rgb(%1,%2,%3)").arg(c.red()).arg(c.green()).arg(c.blue());

    // Heurística para decidir contraste del fondo
    const bool oscuro = (c.red()*0.299 + c.green()*0.587 + c.blue()*0.114) < 128;
    const QString fg = colorCss;
    const QString bg = oscuro ? "white" : "black";

    ui->lblSeleccion->setStyleSheet(
        QString("QLabel {"
                "  color: %1;"
                "  background: %2;"
                "  padding: 8px 12px;"
                "  border-radius: 8px;"
                "  font-weight: bold;"
                "}")
            .arg(fg, bg)
        );
}
